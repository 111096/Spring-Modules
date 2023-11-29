package com.revamp.springarchive.controller;

import com.revamp.springarchive.configuration.DynamicKafkaListener;
import com.revamp.springarchive.service.GenerateFile;
import com.revamp.springarchive.transform.CustomerTransform;
import com.revamp.springdal.dto.CustomerDTO;
import com.revamp.springdal.dto.CustomerOutputDTO;
import com.revamp.springdal.dto.MessageDto;
import com.revamp.springdal.enums.Topic;
import com.revamp.springdal.model.CustomerEntity;
import com.revamp.springdal.model.CustomerRedis;
import com.revamp.springredis.serviceimpl.CustomerRedisServiceImpl;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

@RestController
public class ArchiveController {

    @Autowired
    CustomerRedisServiceImpl customerRedisService;

    List<String> messages = new ArrayList<>();

    List<CustomerEntity> customers = new ArrayList<>();
    @Autowired
    private DynamicKafkaListener listener;

    private CustomerOutputDTO outputDTO = new CustomerOutputDTO();
    private List<CustomerOutputDTO> outputDTOList = new ArrayList<>();

    private CustomerTransform customerTransform = new CustomerTransform();
    private GenerateFile generateFile = new GenerateFile();

    @Scheduled(cron = "0 */1 * * * ?")
    public void perform() throws Exception {
        List<CustomerRedis> completeBatchCash = customerRedisService.findAllFilter();

        if (completeBatchCash != null && !completeBatchCash.isEmpty())

            completeBatchCash.forEach(batchCash -> {
                        ConcurrentMessageListenerContainer<String, MessageDto> multiContainer = listener.newContainer(batchCash.getBatchId(), Topic.BUSINESS.getName(), 3);
                        multiContainer.stop(true);
                        BlockingQueue<ConsumerRecord<String, MessageDto>> completeBatchConsumed = listener.getRecords();
                        List<MessageDto> allCompleteBatchesRecords = completeBatchConsumed.stream().flatMap(record -> Stream.of(record.value())).collect(Collectors.toList());
                        Map<String, List<MessageDto>> groupingBatchData = allCompleteBatchesRecords.stream().collect(groupingBy(MessageDto::getBatchId));
                        Map<String, String> parameterMap = null;
                        for (Map.Entry<String, List<MessageDto>> entry : groupingBatchData.entrySet()) {
                            for (MessageDto messageDto : entry.getValue()) {
                                outputDTO = new CustomerOutputDTO();
                                customerTransform.convertFromDTOToEntity((CustomerDTO) messageDto, outputDTO);
                                outputDTOList.add(outputDTO);
                                String title = "OutPut" + System.currentTimeMillis();
                                parameterMap = new HashMap<>();
                                parameterMap.put("outPut-title", title);
                                parameterMap.put("LocalTime", LocalTime.now() + "");
                                parameterMap.put("InputFileId", entry.getKey());
                                try {
                                    generateFile.GenerateCSVFile(outputDTOList,batchCash,parameterMap,title);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }


                    }
            );
    }
}

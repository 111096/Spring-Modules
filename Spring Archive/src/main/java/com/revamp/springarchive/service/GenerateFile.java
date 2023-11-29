package com.revamp.springarchive.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.revamp.springdal.dto.CustomerOutputDTO;
import com.revamp.springdal.dto.MessageDto;
import com.revamp.springdal.dto.NotifyDto;
import com.revamp.springdal.enums.Topic;
import com.revamp.springdal.model.CustomerRedis;
import com.revamp.springdal.util.ValidateCsv;
import com.revamp.springredis.serviceimpl.CustomerRedisServiceImpl;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Map;

@Component
public class GenerateFile {

    @Autowired
    CustomerRedisServiceImpl customerRedisService;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Qualifier("kafkaArchiveTemplate")
    @Autowired
    private KafkaTemplate<String, NotifyDto> kafkaTemplate;

    public void GenerateCSVFile(List<CustomerOutputDTO> outputDTOList, CustomerRedis batchCash, Map<String, String> parameterMap,String title) throws IOException {
        File file = new File("batch.csv");
        Writer writer = null;

        try {
            writer = new FileWriter(file, true);
            if (!file.exists()) {
                file.createNewFile();
                boolean isFilesSaved = ValidateCsv.customersToCsv(writer, outputDTOList);
                if (isFilesSaved)
                    customerRedisService.deleteById(batchCash.getBatchId());
            } else {
                boolean isFilesSaved = ValidateCsv.customersToCsv(writer, outputDTOList);
                if (isFilesSaved)
                    customerRedisService.deleteById(batchCash.getBatchId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream upload = FileUtils.openInputStream(file);
        DBObject metaData = new BasicDBObject();
        metaData.putAll(parameterMap);
        Object fileID = gridFsTemplate.store(upload, title, "csv", metaData);
        kafkaTemplate.send(Topic.Archive.getName(), new NotifyDto(fileID.toString(),"File Upload Successfully" ));

    }
}

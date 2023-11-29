package com.revamp.springtransform.batch;

import com.google.gson.Gson;
import com.revamp.springdal.dto.CustomerDTO;
import com.revamp.springdal.dto.MessageDto;
import com.revamp.springdal.enums.Topic;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.kafka.KafkaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@StepScope
@Component
public class TransformWriter implements ItemWriter<MessageDto> {

    @Value("#{jobParameters['batchId']}")
    String batchId;

    @Qualifier("kafkaTransformTemplate")
    @Autowired
    private KafkaTemplate<String, MessageDto> kafkaTemplate;

    private KafkaItemWriter<String, MessageDto> kafkaItemWriter;

    @Override
    public void write(List<? extends MessageDto> customers) throws Exception {
        System.out.println("Data Saved for Customers: " + customers);
//        Gson gson = new Gson();
//        customers.forEach(cust -> kafkaTemplate.send(Topic.BUSINESS.getName(), data, cust));
        if (kafkaItemWriter == null) {
            kafkaTemplate.setDefaultTopic(Topic.TRANSFORM.getName());
            kafkaItemWriter = new KafkaItemWriter<>();
            kafkaItemWriter.setKafkaTemplate(kafkaTemplate);
            kafkaItemWriter.setItemKeyMapper(customer -> batchId);
            kafkaItemWriter.setDelete(false);
            kafkaItemWriter.afterPropertiesSet();
        }
        kafkaItemWriter.write(customers);

    }
}

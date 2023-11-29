package com.revamp.springmongo.serviceimpl;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.revamp.springdal.dto.MessageDto;
import com.revamp.springdal.enums.Topic;
import com.revamp.springdal.model.CustomerRedis;
import com.revamp.springdal.repo.CustomerRedisRepository;
import com.revamp.springmongo.service.MongoFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class MongoFileServiceImpl implements MongoFileService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Qualifier("kafkaBatchTemplate")
    @Autowired
    private KafkaTemplate<String, MessageDto> kafkaTemplate;

    @Autowired
    private CustomerRedisRepository redisCustomerRepository;

    @Override
    public String uploadFile(MultipartFile upload, String count) throws IOException {

        //define additional metadata
        DBObject metadata = new BasicDBObject();
        metadata.put("fileSize", upload.getSize());
        metadata.put("topicName", Topic.BATCH.getName());

        //store in database which returns the objectID
        Object fileID = gridFsTemplate.store(upload.getInputStream(), upload.getOriginalFilename(), upload.getContentType(), metadata);

        MessageDto messageDto = new MessageDto(fileID.toString(),Topic.BATCH.getName());
        publisher(Topic.BATCH.getName(), messageDto);
        CustomerRedis redisCustomer = new CustomerRedis();
        redisCustomer.setBatchId(fileID.toString());
        redisCustomer.setBatchName(Topic.BATCH.getName());
        redisCustomer.setCount(BigDecimal.valueOf(Integer.valueOf(count)));
        redisCustomer.setFail(BigDecimal.ZERO);
        redisCustomer.setSucess(BigDecimal.ZERO);
        redisCustomerRepository.saveOrUpdate(redisCustomer);
        //return as a string
        return fileID.toString();
    }

    @Override
    public void publisher(String topic, MessageDto data) {
        Gson gson = new Gson();
        kafkaTemplate.send(topic,data);
    }
}

package com.revamp.springbatch.controller;

import com.revamp.springbatch.service.BatchFileServiceImpl;
import com.revamp.springbatch.service.CustomerEntityServiceImpl;
import com.revamp.springbatch.service.CustomerRedisServiceImpl;
import com.revamp.springdal.model.CustomerEntity;
import com.revamp.springmongo.service.MongoFileServiceImpl;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BatchController {

    @Autowired
    CustomerEntityServiceImpl customerEntityService;

    @Autowired
    BatchFileServiceImpl batchFileService;

    @Autowired
    MongoFileServiceImpl mongoFileService;

    @Autowired
    CustomerRedisServiceImpl redisCustomerService;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations operations;

    List<String> messages = new ArrayList<>();
    List<CustomerEntity> customers = new ArrayList<>();

//    @KafkaListener(groupId = "springbatch", topics = "batch", containerFactory = "kafkaListenerContainerFactory")
//    public ResponseEntity<List<Customer>> getMsgFromTopic(String data) {
//        messages.add(data);
//        LoadFile loadFile = null;
//        try {
//            loadFile = batchFileService.downloadFile(data);
//            customers.addAll(batchFileService.saveOrUpdateCustomerFileInDB(loadFile));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new ResponseEntity<>(customers, HttpStatus.OK);
//    }

    @KafkaListener(groupId = "springbatch", topics = "batch", containerFactory = "kafkaListenerContainerFactory")
    public void getMsgFromTopic(String data) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).addString("fileData", data)
                .toJobParameters();

        jobLauncher.run(job, jobParameters);
//        customers.addAll(batchFileService.saveOrUpdateCustomerFileInDB(loadFile));
    }

    @GetMapping("/getAllRedisCustomer")
    public ResponseEntity<?> getAllRedisCustomer() {
        return new ResponseEntity<>(redisCustomerService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/deleteRedisCustomer/{id}")
    public ResponseEntity<?> getAllRedisCustomer(@PathVariable("id") Integer id ) {
        return new ResponseEntity<>(redisCustomerService.deleteById(id), HttpStatus.OK);
    }
}

package com.revamp.springtransform.controller;

import com.revamp.springdal.dto.MessageDto;
import com.revamp.springdal.model.CustomerEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TransformController {

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

    @KafkaListener(groupId = "revamp-batch", topics = "batch", containerFactory = "kafkaListenerContainerBatchFactory")
    public void getMsgFromTopic(MessageDto data) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).addString("batchId", data.getBatchId()).addString("batchName", data.getBatchName())
                .toJobParameters();

        jobLauncher.run(job, jobParameters);
    }
}

package com.revamp.springtransform.batch;

import com.google.gson.Gson;
import com.revamp.springdal.dto.MessageDto;
import com.revamp.springdal.enums.Topic;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@StepScope
@Component
public class StepResultListener implements StepExecutionListener {

    @Value("#{jobParameters['batchId']}")
    String batchId;

    @Value("#{jobParameters['batchName']}")
    String batchName;

    @Qualifier("kafkaEventTemplate")
    @Autowired
    private KafkaTemplate<String, MessageDto> kafkaTemplate;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Called beforeStep().");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("Called afterStep().");
        kafkaTemplate.send(Topic.EVENT.getName(), batchId, new MessageDto(batchId, batchName));
        return stepExecution.getExitStatus();
    }
}

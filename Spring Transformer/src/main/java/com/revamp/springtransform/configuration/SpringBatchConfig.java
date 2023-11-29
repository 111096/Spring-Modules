package com.revamp.springtransform.configuration;

import com.revamp.springdal.dto.CustomerDTO;
import com.revamp.springdal.dto.MessageDto;
import com.revamp.springtransform.batch.StepResultListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job(Step step) {
        return jobBuilderFactory.get("ETL-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public Step step(
            ItemReader<MessageDto> TransformReader,
            ItemProcessor<MessageDto, MessageDto> TransformProcessor,
            ItemWriter<MessageDto> TransformWriter,
            StepResultListener stepResultListener) {
        return stepBuilderFactory.get("ETL-file-load")
                .<MessageDto, MessageDto>chunk(100)
                .reader(TransformReader)
                .processor(TransformProcessor)
                .writer(TransformWriter)
                .listener(stepResultListener)
                .build();
    }

}
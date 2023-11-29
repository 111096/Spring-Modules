package com.revamp.springbatch.configuration;

import com.revamp.springdal.model.CustomerEntity;
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
            ItemReader<CustomerEntity> Reader,
            ItemProcessor<CustomerEntity, CustomerEntity> Processor,
            ItemWriter<CustomerEntity> Writer) {
        return stepBuilderFactory.get("ETL-file-load")
                .<CustomerEntity, CustomerEntity>chunk(100)
                .reader(Reader)
                .processor(Processor)
                .writer(Writer)
                .build();
    }

}
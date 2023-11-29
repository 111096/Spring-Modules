package com.revamp.springbusiness.configuration;

import com.revamp.springdal.dto.MessageDto;
import com.revamp.springdal.dto.MessageDto;
import com.revamp.springdal.enums.Topic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Properties;

@Configuration
@EnableBatchProcessing
public class SpringBusinessConfig {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    KafkaProperties properties;

    @Value("${kafka.host}")
    public String host;

    @Value("${kafka.port}")
    public String port;

    @Value("${kafka.group.transform}")
    public String group;

    @Bean
    public Job jobBusiness(Step stepBusiness) {
        return jobBuilderFactory.get("ETL-Business-Load")
                .incrementer(new RunIdIncrementer())
                .start(stepBusiness)
                .build();
    }

    @Bean
    public Step stepBusiness(
            ItemReader<MessageDto> BusinessReader,
            ItemProcessor<MessageDto, MessageDto> BusinessProcessor,
            ItemWriter<MessageDto> BusinessWriter) {
        return stepBuilderFactory.get("ETL-Business-file-load")
                .<MessageDto, MessageDto>chunk(100)
                .reader(BusinessReader)
                .processor(BusinessProcessor)
                .writer(BusinessWriter)
                .build();
    }

//    @Bean
//    KafkaItemReader<String, String> kafkaItemReader() {
//        var props = new Properties();
//        props.put(ConsumerConfig.GROUP_ID_CONFIG,group);
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "" + host + ":" + port + "");
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 10);
//        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 300);
//        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 50);
//        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 100);
//        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 1024);
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
//        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
//        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
////        props.putAll(this.properties.buildConsumerProperties());
//
//        return new KafkaItemReaderBuilder<String, String>()
//                .partitions(0)
//                .consumerProperties(props)
//                .name("business-reader")
//                .saveState(true)
//                .topic(Topic.TRANSFORM.getName())
//                .build();
//    }
}
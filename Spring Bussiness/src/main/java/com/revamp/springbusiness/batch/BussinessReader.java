package com.revamp.springbusiness.batch;

import com.google.gson.Gson;
import com.revamp.springdal.dto.CustomerDTO;
import com.revamp.springdal.dto.MessageDto;
import com.revamp.springdal.enums.Topic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.*;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.util.Properties;

@StepScope
@Component
public class BussinessReader implements ItemReader<MessageDto> {

    @Value("${kafka.host}")
    public String host;

    @Value("${kafka.port}")
    public String port;

    @Value("${kafka.group.transform}")
    public String group;

    private KafkaItemReader<String, MessageDto> kafkaItemReader;

    @Override
    public MessageDto read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Gson gson = new Gson();
        if (kafkaItemReader == null) {
            var props = new Properties();
            props.put(ConsumerConfig.GROUP_ID_CONFIG, group);
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "" + host + ":" + port + "");
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 10);
            props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 300);
            props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 50);
            props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 100);
            props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 1024);
            props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
            props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
            props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//        props.putAll(this.properties.buildConsumerProperties());

            kafkaItemReader = new KafkaItemReaderBuilder<String, MessageDto>()
                    .partitions(0)
                    .consumerProperties(props)
                    .name("business-reader")
                    .saveState(true)
                    .topic(Topic.TRANSFORM.getName())
                    .build();
            kafkaItemReader.open(new ExecutionContext());
        }
        return gson.fromJson(String.valueOf(kafkaItemReader.read()),CustomerDTO.class);
    }
}

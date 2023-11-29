package com.revamp.springarchive.configuration;

import com.revamp.springdal.dto.MessageDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.event.ListenerContainerIdleEvent;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.adapter.FilteringMessageListenerAdapter;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.kafka.support.LogIfLevelEnabled;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;


@Component
public class DynamicKafkaListener {

    private static final Logger LOG = LoggerFactory.getLogger(DynamicKafkaListener.class);
    private final static String BOOTSTRAP_ADDRESS = "localhost:9091,localhost:9092,localhost:9093";
    private final static String EMPLOYEE_ARCHIVING_GROUP_NAME = "revamp-archive";
    final CountDownLatch latch = new CountDownLatch(4);
    @Qualifier("kafkaListenerContainerBusinessFactory")
    @Autowired
    private ConcurrentKafkaListenerContainerFactory<String, MessageDto> factory;
    private final ConcurrentMap<String, AbstractMessageListenerContainer<String, MessageDto>> containers
            = new ConcurrentHashMap<>();
    private BlockingQueue<ConsumerRecord<String, MessageDto>> records;

    public ConsumerFactory<String, MessageDto> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProps(), new StringDeserializer(), new JsonDeserializer<>(MessageDto.class));
    }

    public ConcurrentMessageListenerContainer<String, MessageDto> newContainer(String batchId, String topic, int partition) {
        // create a thread safe queue to store the received message
        records = new LinkedBlockingQueue<>();

        RecordFilterStrategy<String, MessageDto> recordFilterStrategy = consumerRecord -> !batchId.contains(consumerRecord.key());

        MessageListener<String, MessageDto> listener = data -> {
            records.add(data);
            latch.countDown();
        };

        FilteringMessageListenerAdapter filteringMessageListenerAdapter = new FilteringMessageListenerAdapter<>(listener, recordFilterStrategy);

        this.factory.setConsumerFactory(consumerFactory());
        this.factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.BATCH);
        this.factory.getContainerProperties().setCommitLogLevel(LogIfLevelEnabled.Level.INFO);
        this.factory.setConcurrency(3);
        this.factory.setRecordFilterStrategy(recordFilterStrategy);

        ConcurrentMessageListenerContainer<String, MessageDto> container =
                this.factory.createContainer(topic);
        container.getContainerProperties().setGroupId(EMPLOYEE_ARCHIVING_GROUP_NAME);
        container.setupMessageListener(filteringMessageListenerAdapter);
        this.containers.put(EMPLOYEE_ARCHIVING_GROUP_NAME, container);
        container.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return container;
    }

    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_ADDRESS);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 10);
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 100);
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 1024);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "30");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return props;
    }

    @EventListener
    public void idle(ListenerContainerIdleEvent event) {
        AbstractMessageListenerContainer<String, MessageDto> container = this.containers.remove(
                event.getContainer(ConcurrentMessageListenerContainer.class).getContainerProperties().getGroupId());
        if (container != null) {
            LOG.info("Stopping idle container");
            container.stop(() -> LOG.info("Stopped"));
        }
    }

    public BlockingQueue<ConsumerRecord<String, MessageDto>> getRecords() {
        return records;
    }
}
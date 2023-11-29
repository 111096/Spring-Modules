package com.revamp.springbusiness.batch;

import com.google.gson.Gson;
import com.revamp.springbusiness.serviceimpl.CustomerServiceEntityImpl;
import com.revamp.springbusiness.transform.CustomerTransform;
import com.revamp.springdal.dto.MessageDto;
import com.revamp.springdal.enums.Topic;
import com.revamp.springdal.model.CustomerEntity;
import com.revamp.springdal.model.CustomerRedis;
import com.revamp.springredis.serviceimpl.CustomerRedisServiceImpl;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.kafka.KafkaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@StepScope
@Component
public class BussinessWriter implements ItemWriter<MessageDto> {

    @Value("#{jobParameters['batchId']}")
    String batchId;

    @Qualifier("kafkaBusinessTemplate")
    @Autowired
    private KafkaTemplate<String, MessageDto> kafkaTemplate;

    private KafkaItemWriter<String, MessageDto> kafkaItemWriter;

    @Autowired
    CustomerServiceEntityImpl customerServiceEntity;

    @Autowired
    CustomerRedisServiceImpl customerBusinessRedisService;

    private CustomerTransform customerTransform = new CustomerTransform();

    @Override
    public void write(List<? extends MessageDto> customers) throws Exception {
        System.out.println("Data Saved for Customers: " + customers);
        CustomerEntity customerEntity = new CustomerEntity();
        CustomerRedis customerRedis = customerBusinessRedisService.findById(batchId);
        if (!customerBusinessRedisService.checkComplete(customerRedis)) {
            if (kafkaItemWriter == null) {
                kafkaTemplate.setDefaultTopic(Topic.BUSINESS.getName());
                kafkaItemWriter = new KafkaItemWriter<>();
                kafkaItemWriter.setKafkaTemplate(kafkaTemplate);
                kafkaItemWriter.setItemKeyMapper(MessageDto::getBatchId);
                kafkaItemWriter.setDelete(false);
                kafkaItemWriter.afterPropertiesSet();
            }
            kafkaItemWriter.write(customers);

            for (MessageDto cust : customers) {
                if (cust.getBatchId().equals(batchId)) {
                    customerEntity = new CustomerEntity();
                    customerTransform.convertFromDTOToEntity(cust, customerEntity);
                    try {
                        customerServiceEntity.saveOrUpdateCustomer(customerEntity);
                        customerRedis.setSucess(customerRedis.getSucess().add(BigDecimal.ONE));
                    } catch (Exception e) {
                        customerRedis.setFail(customerRedis.getFail().add(BigDecimal.ONE));
                    }
                    customerBusinessRedisService.saveOrUpdateCustomerRedis(customerRedis);
                }
            }
        }
    }
}

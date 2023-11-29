package com.revamp.springbatch.batch;

import com.revamp.springdal.model.CustomerEntity;
import com.revamp.springdal.model.CustomerRedis;
import com.revamp.springdal.repo.CustomerEntityRepository;
import com.revamp.springdal.repo.CustomerRedisRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Writer implements ItemWriter<CustomerEntity> {

    @Autowired
    private CustomerEntityRepository customerEntityRepository;

    @Autowired
    private CustomerRedisRepository redisCustomerRepository;

    @Override
    public void write(List<? extends CustomerEntity> customers) throws Exception {
        System.out.println("Data Saved for Customers: " + customers);
        CustomerRedis redisCustomer = new CustomerRedis();
        customers.forEach(customer -> {
            redisCustomer.setId(customer.getId());
            redisCustomer.setName(customer.getName());
            redisCustomer.setPhone(customer.getPhone());
            redisCustomer.setSsn(customer.getSsn());
            redisCustomer.setEmail(customer.getEmail());
            redisCustomer.setUniqueId(customer.getUniqueId());
            redisCustomerRepository.save(redisCustomer);
        });
    }
}

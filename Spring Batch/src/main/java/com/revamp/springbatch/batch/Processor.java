package com.revamp.springbatch.batch;

import com.revamp.springdal.model.CustomerEntity;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class Processor implements ItemProcessor<CustomerEntity, CustomerEntity> {

    @Override
    public CustomerEntity process(CustomerEntity customer) throws Exception {
        System.out.println("Processor :- "+customer);
        return customer;
    }
}

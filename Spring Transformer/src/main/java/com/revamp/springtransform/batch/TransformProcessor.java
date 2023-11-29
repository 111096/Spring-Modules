package com.revamp.springtransform.batch;

import com.revamp.springdal.dto.CustomerDTO;
import com.revamp.springdal.dto.MessageDto;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TransformProcessor implements ItemProcessor<MessageDto, MessageDto> {

    @Override
    public MessageDto process(MessageDto customer) throws Exception {
        System.out.println("Processor :- "+customer);
        return customer;
    }
}

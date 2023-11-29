package com.revamp.springbusiness.batch;

import com.google.gson.Gson;
import com.revamp.springdal.dto.CustomerDTO;
import com.revamp.springdal.dto.CustomerOutputDTO;
import com.revamp.springdal.dto.MessageDto;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class BussinessProcessor implements ItemProcessor<MessageDto, MessageDto> {

    @Override
    public MessageDto process(MessageDto customer) throws Exception {
        System.out.println("Processor :- "+customer);
        return customer;
    }
}

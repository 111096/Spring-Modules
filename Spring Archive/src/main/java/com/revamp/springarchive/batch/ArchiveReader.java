//package com.revamp.springarchive.batch;
//
//import com.google.gson.Gson;
//import com.revamp.springdal.dto.CustomerDTO;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.*;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@StepScope
//@Component
//public class ArchiveReader implements ItemReader<CustomerDTO> {
//
//    @Value("#{jobParameters['fileData']}")
//    String data;
//
//    @Override
//    public CustomerDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//        Gson gson = new Gson();
//        CustomerDTO customerDTO = gson.fromJson(data,CustomerDTO.class);
//        data = null;
//        return customerDTO;
//    }
//}

//package com.revamp.springarchive.batch;
//
//import com.revamp.springdal.dto.CustomerOutputDTO;
//import com.revamp.springdal.dto.MessageDto;
//import com.revamp.springdal.enums.Topic;
//import com.revamp.springdal.util.ValidateCsv;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.util.List;
//
//@Component
//public class ArchiveWriter implements ItemWriter<CustomerOutputDTO> {
//
//    @Override
//    public void write(List<? extends CustomerOutputDTO> customers) throws Exception {
//        System.out.println("Data Saved for Customers: " + customers);
//        File file = new File("batch.csv");
//        java.io.Writer writer = new FileWriter(file, true);
//        if (!file.exists()) {
//            file.createNewFile();
//            ValidateCsv.customersToCsvHeader(writer, (List<CustomerOutputDTO>) customers);
//        } else
//            ValidateCsv.customersToCsv(writer, (List<CustomerOutputDTO>) customers);
//
//        System.out.println(file.getAbsolutePath());
//    }
//}

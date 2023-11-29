//package com.revamp.springarchive.configuration;
//
//import com.revamp.springdal.dto.CustomerDTO;
//import com.revamp.springdal.dto.CustomerOutputDTO;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableBatchProcessing
//public class SpringArchiveConfig {
//
//    @Autowired
//    JobBuilderFactory jobBuilderFactory;
//
//    @Autowired
//    StepBuilderFactory stepBuilderFactory;
//
//    @Bean
//    public Job jobArchive(Step stepArchive) {
//        return jobBuilderFactory.get("ETL-Archive-Load")
//                .incrementer(new RunIdIncrementer())
//                .start(stepArchive)
//                .build();
//    }
//
//    @Bean
//    public Step stepArchive(
//            ItemReader<CustomerDTO> ArchiveReader,
//            ItemProcessor<CustomerDTO, CustomerOutputDTO> ArchiveProcessor,
//            ItemWriter<CustomerOutputDTO> ArchiveWriter) {
//        return stepBuilderFactory.get("ETL-Archive-file-load")
//                .<CustomerDTO, CustomerOutputDTO>chunk(100)
//                .reader(ArchiveReader)
//                .processor(ArchiveProcessor)
//                .writer(ArchiveWriter)
//                .build();
//    }
//
//}
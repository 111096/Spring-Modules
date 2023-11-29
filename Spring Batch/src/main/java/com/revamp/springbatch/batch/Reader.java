package com.revamp.springbatch.batch;

import com.revamp.springbatch.service.BatchFileServiceImpl;
import com.revamp.springdal.model.CustomerEntity;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Component;

@StepScope
@Component
public class Reader implements ItemReader<CustomerEntity> {

    @Autowired
    BatchFileServiceImpl batchFileService;

    @Value("#{jobParameters['fileData']}")
    String data;

    private int nextCustomerIndex = 0;
//    private List<Customer> customerList;
//    Customer nextCustomer = null;
//    LoadFile loadFile = null;
//
//    @Override
//    public Customer read() throws Exception {
//        try {
//            loadFile = batchFileService.downloadFile(data);
//            customerList = ValidateCsv.parseCsvFile(new ByteArrayInputStream(loadFile.getFile()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (nextCustomerIndex < customerList.size()) {
//            nextCustomer = customerList.get(nextCustomerIndex);
//            nextCustomerIndex++;
//        }else{
//            return null;
//        }
//
//        return nextCustomer;
//    }

    public ItemReader<CustomerEntity> itemReader() throws Exception {
        nextCustomerIndex++;
        GridFsResource gridFsResource = batchFileService.retriveFileResource(data);
        FlatFileItemReader<CustomerEntity> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new InputStreamResource(gridFsResource.getInputStream()));
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(nextCustomerIndex);
        flatFileItemReader.setLineMapper(lineMapper());
        flatFileItemReader.open(new ExecutionContext());
        return flatFileItemReader;
    }

    public LineMapper<CustomerEntity> lineMapper() {

        DefaultLineMapper<CustomerEntity> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "name", "email", "ssn", "phone", "uniqueId");

        BeanWrapperFieldSetMapper<CustomerEntity> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(CustomerEntity.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }

    @Override
    public CustomerEntity read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return itemReader().read();
    }
}

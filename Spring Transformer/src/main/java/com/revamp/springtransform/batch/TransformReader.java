package com.revamp.springtransform.batch;

import com.revamp.springdal.dto.MessageDto;
import com.revamp.springdal.util.UniqueNumber;
import com.revamp.springtransform.serviceimpl.BatchFileServiceImpl;
import com.revamp.springdal.dto.CustomerDTO;
import com.revamp.springdal.dto.LoadFile;
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
public class TransformReader implements ItemReader<MessageDto> {

    @Autowired
    BatchFileServiceImpl batchFileService;

    @Value("#{jobParameters['batchId']}")
    String batchId;

    @Value("#{jobParameters['batchName']}")
    String batchName;

    private LoadFile loadFile = null;
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

    public ItemReader<MessageDto> itemReader() throws Exception {
        nextCustomerIndex++;
        GridFsResource gridFsResource = batchFileService.retriveFileResource(batchId);
        FlatFileItemReader<MessageDto> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new InputStreamResource(gridFsResource.getInputStream()));
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(nextCustomerIndex);
        flatFileItemReader.setLineMapper(lineMapper());
        flatFileItemReader.open(new ExecutionContext());
        return flatFileItemReader;
    }

    public LineMapper<MessageDto> lineMapper() {

        DefaultLineMapper<MessageDto> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "name", "email", "ssn", "phone", "uniqueId");

        BeanWrapperFieldSetMapper<MessageDto> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(CustomerDTO.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }

    @Override
    public MessageDto read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        loadFile = batchFileService.downloadFile(batchId);
        MessageDto customerDTO = itemReader().read();
        if (customerDTO != null) {
            customerDTO.setBatchId(batchId);
            customerDTO.setBatchName(batchName);
        }
        return customerDTO;
    }
}

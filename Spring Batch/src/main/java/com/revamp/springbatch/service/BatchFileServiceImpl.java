package com.revamp.springbatch.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.revamp.springdal.model.CustomerEntity;
import com.revamp.springdal.model.LoadFile;
import com.revamp.springdal.repo.CustomerEntityRepository;
import com.revamp.springdal.utils.ValidateCsv;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class BatchFileServiceImpl implements BatchFileService {

    @Autowired
    CustomerEntityRepository customerEntityRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations operations;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public LoadFile downloadFile(String id) throws IOException {
        //search file
        GridFSFile gridFSFile = retriveFile(id);
        //convert uri to byteArray
        //save data to LoadFile class
        LoadFile loadFile = new LoadFile();

        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
            loadFile.setFilename(gridFSFile.getFilename());

            loadFile.setFileType(gridFSFile.getMetadata().get("_contentType").toString());

            loadFile.setFileSize(gridFSFile.getMetadata().get("fileSize").toString());

            loadFile.setBatchName(gridFSFile.getMetadata().get("topicName").toString());


            loadFile.setFile(IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()));
        }

        return loadFile;
    }

    @Override
    public GridFSFile retriveFile(String id) {
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        return gridFSFile;
    }

    @Override
    public GridFsResource retriveFileResource(String id) {
        GridFsResource gridFsResource = gridFsTemplate.getResource(retriveFile(id));
        return gridFsResource;
    }

    @Override
    public List<CustomerEntity> saveOrUpdateCustomerFileInDB(LoadFile loadFile) {
        List<CustomerEntity> list = ValidateCsv.parseCsvFile(new ByteArrayInputStream(loadFile.getFile()));
//        try {
//            batchJob(loadFile.getFilename());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return customerEntityRepository.saveAll(list);
    }

//    public BatchStatus batchJob(String fileName) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
//
//
//        Map<String, JobParameter> maps = new HashMap<>();
//        maps.put("time", new JobParameter(System.currentTimeMillis()));
//        maps.put("fileName", new JobParameter(fileName));
//        JobParameters parameters = new JobParameters(maps);
//        JobExecution jobExecution = jobLauncher.run(job, parameters);
//
//        System.out.println("JobExecution: " + jobExecution.getStatus());
//
//        System.out.println("Batch is Running...");
//        while (jobExecution.isRunning()) {
//            System.out.println("...");
//        }
//
//        return jobExecution.getStatus();
//    }
}

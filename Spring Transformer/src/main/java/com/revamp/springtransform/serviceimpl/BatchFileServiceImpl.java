package com.revamp.springtransform.serviceimpl;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.revamp.springdal.dto.LoadFile;
import com.revamp.springtransform.service.BatchFileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BatchFileServiceImpl implements BatchFileService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations operations;
    
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

}

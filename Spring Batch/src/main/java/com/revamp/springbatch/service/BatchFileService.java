package com.revamp.springbatch.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.revamp.springdal.model.CustomerEntity;
import com.revamp.springdal.model.LoadFile;
import org.springframework.data.mongodb.gridfs.GridFsResource;

import java.io.IOException;
import java.util.List;

public interface BatchFileService {

    public LoadFile downloadFile(String id) throws IOException;

    public GridFSFile retriveFile(String id);

    GridFsResource retriveFileResource(String id);

    public List<CustomerEntity> saveOrUpdateCustomerFileInDB(LoadFile loadFile);

}

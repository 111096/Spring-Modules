package com.revamp.springtransform.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.revamp.springdal.dto.LoadFile;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;

import java.io.IOException;

public interface BatchFileService {

    public LoadFile downloadFile(String id) throws IOException;

    public GridFSFile retriveFile(String id);

    GridFsResource retriveFileResource(String id);

}

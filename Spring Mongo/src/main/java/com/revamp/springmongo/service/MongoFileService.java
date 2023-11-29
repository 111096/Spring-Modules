package com.revamp.springmongo.service;

import com.revamp.springdal.dto.MessageDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MongoFileService {
    public String uploadFile(MultipartFile upload,String count) throws IOException;

    public void publisher(String topic, MessageDto data);
}

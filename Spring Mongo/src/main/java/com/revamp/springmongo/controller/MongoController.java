package com.revamp.springmongo.controller;

import com.revamp.springdal.exception.FileNoContentException;
import com.revamp.springdal.exception.ResourceNotFoundException;
import com.revamp.springmongo.serviceimpl.MongoFileServiceImpl;
import com.revamp.springredis.serviceimpl.CustomerRedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class MongoController {

    @Autowired
    MongoFileServiceImpl batchFileService;

    @Autowired
    CustomerRedisServiceImpl redisCustomerService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("count") String count) throws IOException, FileNoContentException, ResourceNotFoundException {
        return new ResponseEntity<>(batchFileService.uploadFile(file, count), HttpStatus.OK);
    }

}

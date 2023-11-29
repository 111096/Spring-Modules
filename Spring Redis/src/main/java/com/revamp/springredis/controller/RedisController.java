package com.revamp.springredis.controller;

import com.revamp.springredis.serviceimpl.CustomerRedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("redis")
public class RedisController {

    @Autowired
    CustomerRedisServiceImpl customerArchiveRedisService;

    @GetMapping("/getAllRedisCustomer")
    public ResponseEntity<?> getAllRedisCustomer() {
        return new ResponseEntity<>(customerArchiveRedisService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/deleteRedisCustomer/{id}")
    public ResponseEntity<?> deleteRedisCustomer(@PathVariable("id") String id) {
        return new ResponseEntity<>(customerArchiveRedisService.deleteById(id), HttpStatus.OK);
    }

    @PostMapping("/deleteAllRedisCustomer")
    public ResponseEntity<?> deleteAllRedisCustomer() {
        return new ResponseEntity<>(customerArchiveRedisService.deleteAll(), HttpStatus.OK);
    }
}

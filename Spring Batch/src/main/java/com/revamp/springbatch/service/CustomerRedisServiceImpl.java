package com.revamp.springbatch.service;

import com.revamp.springdal.model.CustomerRedis;
import com.revamp.springdal.repo.CustomerRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerRedisServiceImpl implements CustomerRedisService {

    @Autowired
    CustomerRedisRepository redisCustomerRedisRepository;


    @Override
    public CustomerRedis saveOrUpdateCustomerRedis(CustomerRedis redisCustomer) {
        redisCustomer = redisCustomerRedisRepository.save(redisCustomer);
        return redisCustomer;
    }

    @Override
    public CustomerRedis findById(Integer id) {
        CustomerRedis redisCustomer = redisCustomerRedisRepository.findById(id);
        return redisCustomer;
    }

    @Override
    public List<CustomerRedis> findAll() {
        List<CustomerRedis> redisCustomerList = redisCustomerRedisRepository.findAll();
        return redisCustomerList;
    }

    @Override
    public String deleteById(Integer id) {
        return redisCustomerRedisRepository.deleteById(id);
    }

}

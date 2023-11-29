package com.revamp.springredis.serviceimpl;

import com.revamp.springdal.model.CustomerRedis;
import com.revamp.springdal.repo.CustomerRedisRepository;
import com.revamp.springredis.service.CustomerRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerRedisServiceImpl implements CustomerRedisService {

    @Autowired
    CustomerRedisRepository redisCustomerRedisRepository;


    @Override
    public CustomerRedis saveOrUpdateCustomerRedis(CustomerRedis redisCustomer) {
        redisCustomer = redisCustomerRedisRepository.saveOrUpdate(redisCustomer);
        return redisCustomer;
    }

    @Override
    public CustomerRedis findById(String id) {
        CustomerRedis redisCustomer = redisCustomerRedisRepository.findById(id);
        return redisCustomer;
    }

    @Override
    public List<CustomerRedis> findAll() {
        List<CustomerRedis> redisCustomerList = redisCustomerRedisRepository.findAll();
        return redisCustomerList;
    }

    @Override
    public List<CustomerRedis> findAllFilter() {
        List<CustomerRedis> redisCustomerList = redisCustomerRedisRepository.findAllFilter();
        return redisCustomerList;
    }

    @Override
    public String deleteById(String id) {
        return redisCustomerRedisRepository.deleteById(id);
    }

    @Override
    public String deleteAll() {
        return redisCustomerRedisRepository.deleteAll();
    }

    @Override
    public boolean checkComplete(CustomerRedis customerRedis) {
        return redisCustomerRedisRepository.checkComplete(customerRedis);
    }

}

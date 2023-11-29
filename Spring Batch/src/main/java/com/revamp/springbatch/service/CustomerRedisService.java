package com.revamp.springbatch.service;


import com.revamp.springdal.model.CustomerRedis;

import java.util.List;

public interface CustomerRedisService {


    public CustomerRedis saveOrUpdateCustomerRedis(CustomerRedis redisCustomer);

    public CustomerRedis findById(Integer id);

    public List<CustomerRedis> findAll();

    public String deleteById(Integer id);

}

package com.revamp.springredis.service;


import com.revamp.springdal.model.CustomerRedis;

import java.util.List;

public interface CustomerRedisService {


    public CustomerRedis saveOrUpdateCustomerRedis(CustomerRedis redisCustomer);

    public CustomerRedis findById(String id);

    public List<CustomerRedis> findAll();

    public List<CustomerRedis> findAllFilter();

    public String deleteById(String id);

    public String deleteAll();

    public boolean checkComplete(CustomerRedis customerRedis);
}

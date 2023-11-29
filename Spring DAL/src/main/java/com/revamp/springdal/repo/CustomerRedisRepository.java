package com.revamp.springdal.repo;

import com.revamp.springdal.model.CustomerRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomerRedisRepository {
    private static final String HASH_KEY = "CustomerRedis";

    @Autowired
    private RedisTemplate redisTemplate;

    public CustomerRedis saveOrUpdate(CustomerRedis redisCustomer) {
        redisTemplate.opsForHash().put(HASH_KEY, redisCustomer.getBatchId(), redisCustomer);
        return redisCustomer;
    }

    public List<CustomerRedis> findAll() {
        return redisTemplate.opsForHash().values(HASH_KEY);
    }

    public List<CustomerRedis> findAllFilter() {
        return (List<CustomerRedis>) redisTemplate.opsForHash().values(HASH_KEY).stream().filter(b -> checkComplete((CustomerRedis) b)).collect(Collectors.toList());
    }

    public CustomerRedis findById(String id) {
        return (CustomerRedis) redisTemplate.opsForHash().get(HASH_KEY, id);
    }

    public String deleteById(String id) {
        redisTemplate.opsForHash().delete(HASH_KEY, id);
        return "Removed Successfull";
    }

    public String deleteAll() {
        List<CustomerRedis> customerRedis = findAll();
        customerRedis.forEach(cust -> deleteById(cust.getBatchId()));
        return "Removed Successfull";
    }

    public boolean checkComplete(CustomerRedis customerRedis) {
        if (customerRedis == null)
            return false;
        return (customerRedis.getSucess().add(customerRedis.getFail())).equals(customerRedis.getCount());
    }

}


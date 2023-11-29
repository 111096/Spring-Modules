package com.revamp.springbusiness.service;

import com.revamp.springdal.model.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerEntityService {

    public CustomerEntity saveOrUpdateCustomer(CustomerEntity customer);

    public Optional<CustomerEntity> findById(Integer id);

    public List<CustomerEntity> findAll();

    public void deleteById(Integer id);

}

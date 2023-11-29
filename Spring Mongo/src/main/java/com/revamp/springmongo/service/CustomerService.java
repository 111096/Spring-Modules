package com.revamp.springmongo.service;

import com.revamp.springdal.model.CustomerMongo;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    public CustomerMongo saveOrUpdateCustomer(CustomerMongo customer);

    public Optional<CustomerMongo> findById(Integer id);

    public List<CustomerMongo> findAll();

    public void deleteById(Integer id);

}

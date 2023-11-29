package com.revamp.springbatch.service;

import com.revamp.springdal.model.CustomerEntity;
import com.revamp.springdal.repo.CustomerEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerEntityServiceImpl implements CustomerEntityService {

    @Autowired
    CustomerEntityRepository customerEntityRepository;


    @Override
    public CustomerEntity saveOrUpdateCustomer(CustomerEntity customer) {
        customer = customerEntityRepository.save(customer);
        return customer;
    }

    @Override
    public Optional<CustomerEntity> findById(Integer id) {
        Optional customer = customerEntityRepository.findById(id);
        return customer;
    }

    @Override
    public List<CustomerEntity> findAll() {
        List<CustomerEntity> customerList = customerEntityRepository.findAll();
        return customerList;
    }

    @Override
    public void deleteById(Integer id) {
        customerEntityRepository.deleteById(id);
    }

}

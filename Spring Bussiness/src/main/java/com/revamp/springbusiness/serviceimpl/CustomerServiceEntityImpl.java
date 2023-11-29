package com.revamp.springbusiness.serviceimpl;

import com.revamp.springbusiness.service.CustomerEntityService;
import com.revamp.springdal.model.CustomerEntity;
import com.revamp.springdal.repo.CustomerEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceEntityImpl implements CustomerEntityService {

    @Autowired
    CustomerEntityRepository customerRepository;

    @Override
    public CustomerEntity saveOrUpdateCustomer(CustomerEntity customer) {
        customer = customerRepository.save(customer);
        return customer;
    }

    @Override
    public Optional<CustomerEntity> findById(Integer id) {
        Optional customer = customerRepository.findById(id);
        return customer;
    }

    @Override
    public List<CustomerEntity> findAll() {
        List<CustomerEntity> customerList = customerRepository.findAll();
        return customerList;
    }

    @Override
    public void deleteById(Integer id) {
        customerRepository.deleteById(id);
    }

}
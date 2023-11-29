package com.revamp.springmongo.serviceimpl;

import com.revamp.springdal.model.CustomerMongo;
import com.revamp.springdal.repo.CustomerMongoRepository;
import com.revamp.springmongo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerMongoRepository customerRepository;

    @Override
    public CustomerMongo saveOrUpdateCustomer(CustomerMongo customer) {
        customer = customerRepository.save(customer);
        return customer;
    }

    @Override
    public Optional<CustomerMongo> findById(Integer id) {
        Optional customer = customerRepository.findById(id);
        return customer;
    }

    @Override
    public List<CustomerMongo> findAll() {
        List<CustomerMongo> customerList = customerRepository.findAll();
        return customerList;
    }

    @Override
    public void deleteById(Integer id) {
        customerRepository.deleteById(id);
    }

}
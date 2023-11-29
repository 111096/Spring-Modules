package com.revamp.springdal.repo;

import com.revamp.springdal.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, Integer> {

    public CustomerEntity findByName(String name);
}

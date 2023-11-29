package com.revamp.springdal.repo;

import com.revamp.springdal.model.CustomerMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerMongoRepository extends MongoRepository<CustomerMongo, Integer> {
}

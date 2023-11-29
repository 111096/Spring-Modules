package com.revamp.springtransform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.revamp.*")
@EnableRedisRepositories("com.revamp.springdal.repo")
@EnableMongoRepositories("com.revamp.springdal.repo")
public class SpringTransformerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringTransformerApplication.class, args);
    }

}

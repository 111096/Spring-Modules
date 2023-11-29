package com.revamp.springarchive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = "com.revamp.*")
@EnableRedisRepositories("com.revamp.springdal.repo")
@EnableScheduling
public class SpringArchiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringArchiveApplication.class, args);
    }

}

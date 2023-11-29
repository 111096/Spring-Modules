package com.revamp.springbusiness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.revamp.*")
@EnableJpaRepositories("com.revamp.springdal.repo")
@EnableRedisRepositories("com.revamp.springdal.repo")
public class SpringBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBusinessApplication.class, args);
    }

}

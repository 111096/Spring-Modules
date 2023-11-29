package com.revamp.springdal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories("com.revamp.springdal.repo")
public class SpringDalApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDalApplication.class, args);
	}

}

package io.dickson.liquibasesample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class LiquibaseSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiquibaseSampleApplication.class, args);
    }

}

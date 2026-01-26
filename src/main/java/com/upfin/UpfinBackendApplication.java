package com.upfin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.upfin")
@EnableJpaRepositories(basePackages = "com.upfin.lead")
@EntityScan(basePackages = "com.upfin.lead")
public class UpfinBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpfinBackendApplication.class, args);
    }
}
package com.banquito.core.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CoreAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoreAdminApplication.class, args);
    }
}

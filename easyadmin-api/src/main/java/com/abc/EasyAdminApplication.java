package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.abc.*")
public class EasyAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyAdminApplication.class, args);
    }
}

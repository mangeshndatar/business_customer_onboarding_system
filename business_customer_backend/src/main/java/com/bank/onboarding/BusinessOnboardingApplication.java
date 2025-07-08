package com.bank.onboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BusinessOnboardingApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusinessOnboardingApplication.class, args);
    }
}
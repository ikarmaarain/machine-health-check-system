package com.example.machinehealthchecksystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MachineHealthCheckSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MachineHealthCheckSystemApplication.class, args);
    }
}

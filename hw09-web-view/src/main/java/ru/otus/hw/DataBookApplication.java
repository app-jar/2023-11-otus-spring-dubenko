package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class DataBookApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataBookApplication.class, args);
    }
}
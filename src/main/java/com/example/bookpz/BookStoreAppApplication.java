package com.example.bookpz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BookStoreAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookStoreAppApplication.class, args);
    }


}

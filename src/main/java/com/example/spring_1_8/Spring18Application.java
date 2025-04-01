package com.example.spring_1_8;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Spring18Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Spring18Application.class, args);
    }

}

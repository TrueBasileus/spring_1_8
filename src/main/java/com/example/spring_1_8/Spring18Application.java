package com.example.spring_1_8;


import com.example.spring_1_8.dao.GenreDao;
import com.example.spring_1_8.domain.Genre;
import com.example.spring_1_8.service.AuthorService;
import com.example.spring_1_8.shell.LibraryCommands;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class Spring18Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Spring18Application.class, args);
    }

}

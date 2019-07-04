package com.spark.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class RunApp extends SpringBootServletInitializer {

    ///Run app
    public static void main(String[] args) {
        SpringApplication.run(RunApp.class, args);

    }
}

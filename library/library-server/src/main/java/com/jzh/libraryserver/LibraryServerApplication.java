package com.jzh.libraryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LibraryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryServerApplication.class, args);
    }

}

package com.chatwindow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//The main method.
//Everything starts here
@SpringBootApplication
public class SpringBootChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                SpringBootChatApplication.class , args);
    }
}
package com.fzcode.apiblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class ApiBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiBlogApplication.class, args);
    }
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

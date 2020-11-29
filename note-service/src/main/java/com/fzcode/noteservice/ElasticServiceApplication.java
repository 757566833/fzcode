package com.fzcode.noteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ElasticServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticServiceApplication.class, args);

	}

}

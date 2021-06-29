package com.fzcode.service.note;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableJpaAuditing
@SpringBootApplication
public class NoteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteServiceApplication.class, args);

	}
	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
	}
}

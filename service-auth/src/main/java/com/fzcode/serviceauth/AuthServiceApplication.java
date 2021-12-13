package com.fzcode.serviceauth;

import com.fzcode.internalcommon.handler.CustomResponseErrorHandler;
import com.fzcode.internalcommon.http.Http;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
	}

	@Bean
	public Http http(){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		return  new Http(restTemplate);
	}
}

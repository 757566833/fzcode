package com.fzcode.authservice.http;

import com.fzcode.authservice.config.Services;
import com.fzcode.authservice.dto.pri.mail.EmailReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class Mail {
    private Services services;
    private static WebClient webClient;
    @Autowired
    public void setServices(Services services) {
        this.services = services;
        Mail.webClient = WebClient.create(services.getHost().get("mail"));
    }


    public static String getRegisterCode(String email) {
        EmailReqDTO emailReqDTO = new EmailReqDTO();
        emailReqDTO.setEmail(email);
        return Mail.webClient
                .post()
                .uri("/pri/email/register/code")
                .bodyValue(emailReqDTO)
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
//        return webClient.exchange().block().bodyToMono(String.class).block();
    }
}

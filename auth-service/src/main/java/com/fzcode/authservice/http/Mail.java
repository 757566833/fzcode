package com.fzcode.authservice.http;

import com.fzcode.authservice.config.Config;
import com.fzcode.authservice.dto.pri.mail.EmailReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class Mail {
    private static Config config;
    private static WebClient webClient;

    @Autowired
    public void setConfig(Config config) {

        Mail.config = config;
        Mail.webClient = WebClient.create(Mail.config.getMailService());
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

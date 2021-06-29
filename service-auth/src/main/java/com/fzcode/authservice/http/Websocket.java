package com.fzcode.authservice.http;

import com.fzcode.authservice.config.Oauth;
import com.fzcode.authservice.config.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class Websocket {
    private Services services;
    private static WebClient webClient;
    @Autowired
    public void setServices(Services services) {
        this.services = services;
        Websocket.webClient = WebClient.create(services.getHost().get("websocket"));
    }

    public static void sendUserCode(String socketId, String token, String role) {
//        EmailReqDTO emailReqDTO = new EmailReqDTO();
//        emailReqDTO.setEmail(email);


        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("socketId", socketId);
        map.add("token", token);
        map.add("role", role);
        Websocket.webClient
                .post()
                .uri("/login")
                .bodyValue(map)
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
    }

}

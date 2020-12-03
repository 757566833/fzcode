package com.fzcode.authservice.http;

import com.fzcode.authservice.config.Config;
import com.fzcode.authservice.dto.pri.mail.EmailReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;

@Component
public class Websocket {
    private static Config config;
    private static WebClient webClient;

    @Autowired
    public void setConfig(Config config) {

        Websocket.config = config;
        Websocket.webClient = WebClient.create(Websocket.config.getWebsocket());
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

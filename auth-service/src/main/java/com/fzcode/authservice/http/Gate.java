package com.fzcode.authservice.http;

import com.fzcode.authservice.config.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Component
public class Gate {
    private Services services;
    private static WebClient webClient;
    @Autowired
    public void setServices(Services services) {
        this.services = services;
        Gate.webClient = WebClient.create(services.getHost().get("gate"));
    }

    public static void updateAuthority(String account, String authority) {
//        EmailReqDTO emailReqDTO = new EmailReqDTO();
//        emailReqDTO.setEmail(email);
        Map<String, String> map = new HashMap<>();
        map.put("account", account);
        map.put("authority", authority);
        Gate.webClient
                .post()
                .uri("/pri/refresh/authority")
                .bodyValue(map)
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
//        return webClient.exchange().block().bodyToMono(String.class).block();
    }
}

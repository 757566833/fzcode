package com.fzcode.cloudgate.http;

import com.alibaba.fastjson.JSON;
import com.fzcode.cloudgate.config.Services;
import com.fzcode.cloudgate.dto.common.AuthorityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class Auth {
    private  Services services;
    @Autowired
    public void setServices(Services services) {
        this.services = services;
    }

    public Mono<AuthorityDTO> getAuthority(String email) {
        System.out.println(JSON.toJSONString("getAuthority"));
        WebClient webClient = WebClient.create(services.getHost().get("auth"));
        Map<String, String> map = new HashMap<>();
        map.put("account", email);
        return webClient.post().uri("/pri/authority/get")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(map).retrieve().bodyToMono(AuthorityDTO.class);

    }
}

package com.fzcode.cloudgate.http;

import com.fzcode.cloudgate.config.Services;
import com.fzcode.internalcommon.dto.common.AuthorityDTO;
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

    private  WebClient webClient;
    @Autowired
    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<AuthorityDTO> getAuthority(String email) {
        Map<String, String> map = new HashMap<>();
        map.put("account", email);
        return webClient.post().uri(services.getService().getAuth().getHost()+"/authority/get")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(map).retrieve().bodyToMono(AuthorityDTO.class);

    }
}

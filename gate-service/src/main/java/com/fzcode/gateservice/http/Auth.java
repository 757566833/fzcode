package com.fzcode.gateservice.http;

import com.alibaba.fastjson.JSON;
import com.fzcode.gateservice.config.Config;
import com.fzcode.gateservice.dto.common.AuthorityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Auth {
    private static Config config;
    private static WebClient webClient;

    @Autowired
    public void setConfig(Config config) {

        Auth.config = config;
        Auth.webClient = WebClient.create(Auth.config.getAuthService());
    }

    public static Mono<AuthorityDTO> getAuthority(String email) {
        System.out.println(JSON.toJSONString("getAuthority"));
        Map<String, String> map = new HashMap<>();
        map.put("account", email);
        return webClient.post().uri("/pri/authority/get").header("Content-type",MediaType.APPLICATION_JSON_VALUE).bodyValue(map).retrieve().bodyToMono(AuthorityDTO.class);

    }
}

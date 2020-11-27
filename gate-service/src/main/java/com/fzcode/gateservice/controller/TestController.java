package com.fzcode.gateservice.controller;

import com.fzcode.gateservice.dto.common.TokenDTO;
import com.fzcode.gateservice.util.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@RestController
public class TestController {
    private String key;

    @Value("${auth.secret}")
    public void setKey(String secret) {
        this.key = secret;
    }

    @GetMapping("/current")
    public Mono<String> test(Mono<Principal> principalMono) {
        return Mono.just(this.key);
    }

    @PostMapping("/auth2/test2")
    public String test2(@RequestBody TokenDTO username) {
        return JwtUtils.parseToken(username.getToken());
    }
}

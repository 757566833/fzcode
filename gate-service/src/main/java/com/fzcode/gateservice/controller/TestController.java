package com.fzcode.gateservice.controller;

import com.fzcode.gateservice.flow.AuthorityFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
public class TestController {
    private String key;
    AuthorityFlow authorityFlow;

    @Autowired
    public void setAuthorityFlow(AuthorityFlow authorityFlow) {
        this.authorityFlow = authorityFlow;
    }

    @Value("${auth.secret}")
    public void setKey(String secret) {
        this.key = secret;
    }

    @GetMapping("/current")
    public Mono<String> test(Mono<Principal> principalMono) {
        return Mono.just(this.key);
    }

}

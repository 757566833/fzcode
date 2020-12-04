package com.fzcode.authservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class Config {
    @Value("${domain.websocket}")
    private String websocket;

    @Value("${auth.clientId}")
    private String client_id;

    @Value("${auth.clientSecret}")
    private String client_secret;

    @Value("${uri.mailService}")
    private String mailService;

    @Value("${uri.mailService}")
    private String gateService;
}
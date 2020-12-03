package com.fzcode.gateservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class Config {
    @Value("${services.uri}")
    public String url ;

    @Value("${uri.authService}")
    private String authService ;
}
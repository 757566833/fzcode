package com.fzcode.service.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "services")
@Data
public class Services {
    private Map<String, String> host;
    private Map<String, String> auth;
}

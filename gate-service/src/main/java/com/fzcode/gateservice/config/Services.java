package com.fzcode.gateservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "services")
@Data
public class Services {
    private Map<String, String> host;
    private Map<String, String> auth;
}

package com.fzcode.serviceauth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "github")
@Data
public class Github {
    private String githubAuthorize;
    private String loginUrl;
    private String successUrl;
}

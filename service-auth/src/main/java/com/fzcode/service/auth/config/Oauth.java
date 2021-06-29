package com.fzcode.service.auth.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oauth")
@Data
public class Oauth {
    private String clientId;
    private String clientSecret;

}
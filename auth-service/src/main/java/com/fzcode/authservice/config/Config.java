package com.fzcode.authservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Value("${domain.websocket}")
    public String websocket ;

    @Value("${auth.clientId}")
    public String client_id ;

    @Value("${auth.clientSecret}")
    public String client_secret ;

    public String getWebsocket() {
        return websocket;
    }

    public void setWebsocket(String websocket) {
        this.websocket = websocket;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }
}
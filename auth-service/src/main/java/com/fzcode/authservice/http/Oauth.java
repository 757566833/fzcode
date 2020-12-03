package com.fzcode.authservice.http;

import com.fzcode.authservice.config.Config;
import com.fzcode.authservice.dto.pri.mail.EmailReqDTO;
import com.fzcode.authservice.dto.response.GithubAccessToken;
import com.fzcode.authservice.dto.response.GithubUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.function.Consumer;

@Component
public class Oauth {
    private static Config config;

    @Autowired
    public void setConfig(Config config) {

        Oauth.config = config;
    }

    public static GithubAccessToken getGithubAccessToken(String code) {
        return WebClient.create("https://github.com")
                .get()
                .uri("/login/oauth/access_token?client_id="
                        + config.getClient_id() +
                        "&client_secret=" +
                        config.getClient_secret() +
                        "&code=" + code)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .block()
                .bodyToMono(GithubAccessToken.class)
                .block();

    }

    public static GithubUserInfo getGithubUserInfo(String access_token) {
        return WebClient.create("https://api.github.com")
                .get()
                .uri("/user")
                .header("Authorization","token " +access_token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(GithubUserInfo.class)
                .block();

    }
}

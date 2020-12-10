package com.fzcode.authservice.http;

import com.fzcode.authservice.config.Oauth;
import com.fzcode.authservice.dto.response.GithubAccessToken;
import com.fzcode.authservice.dto.response.GithubUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class Auth {
    private static Oauth oauth;

    @Autowired
    public void setOauth(Oauth oauth) {
        Auth.oauth = oauth;
    }

    public static GithubAccessToken getGithubAccessToken(String code) {
        return WebClient.create("https://github.com")
                .get()
                .uri("/login/oauth/access_token?client_id="
                        + oauth.getClientId() +
                        "&client_secret=" +
                        oauth.getClientSecret() +
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

package com.fzcode.serviceauth.http;

import com.fzcode.internalcommon.http.Http;
import com.fzcode.serviceauth.config.Oauth;
import com.fzcode.internalcommon.dto.serviceauth.common.GithubAccessToken;
import com.fzcode.internalcommon.dto.serviceauth.common.GithubUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class Auth {
    private Oauth oauth;

    @Autowired
    public void setOauth(Oauth oauth) {
        this.oauth = oauth;
    }

//    private  static RestTemplate  restTemplate;
//    @Autowired
//    public void setRestTemplate(RestTemplate restTemplate){
//        this.restTemplate = restTemplate;
//    }

    private Http http;
    @Autowired
    public void setHttp(Http http){
        this.http = http;
    }

    public static GithubAccessToken getGithubAccessToken(String code) {
        return Auth.restTemplate.getForObject("https://github.com"+"/login/oauth/access_token?client_id=" + oauth.getClientId() + "&client_secret=" + oauth.getClientSecret() + "&code=" + code,GithubAccessToken.class);
    }

    public static GithubUserInfo getGithubUserInfo(String access_token) {
        LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("Authorization", Collections.singletonList("token " +access_token));
        HttpEntity<String> request =  new HttpEntity<String>(null, headers);
        return  restTemplate.exchange("https://api.github.com"+"/user", HttpMethod.GET, request, GithubUserInfo.class).getBody();

    }
}

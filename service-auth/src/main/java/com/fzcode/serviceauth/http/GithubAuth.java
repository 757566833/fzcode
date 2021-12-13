package com.fzcode.serviceauth.http;

import com.fzcode.internalcommon.dto.serviceauth.request.GithubAccessTokenRequest;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.internalcommon.http.Http;
import com.fzcode.serviceauth.config.Oauth;
import com.fzcode.internalcommon.dto.serviceauth.common.GithubAccessToken;
import com.fzcode.internalcommon.dto.serviceauth.common.GithubUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class GithubAuth {
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

    public GithubAccessToken getGithubAccessToken(String code) throws CustomizeException {
        GithubAccessTokenRequest githubAccessTokenRequest = new GithubAccessTokenRequest();
        githubAccessTokenRequest.setClient_id(this.oauth.getClientId());
        githubAccessTokenRequest.setCode(code);
        githubAccessTokenRequest.setClient_secret(this.oauth.getClientSecret());
        return  http.get("https://github.com/login/oauth/access_token",githubAccessTokenRequest,GithubAccessToken.class);
//        return Auth.restTemplate.getForObject("https://github.com"+"/login/oauth/access_token?client_id=" + oauth.getClientId() + "&client_secret=" + oauth.getClientSecret() + "&code=" + code,GithubAccessToken.class);
    }

    public GithubUserInfo getGithubUserInfo(String access_token) throws CustomizeException {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", Collections.singletonList("token " +access_token));
        return  http.get("https://api.github.com/user",headers,GithubUserInfo.class);
//        return  restTemplate.exchange("https://api.github.com"+"/user", HttpMethod.GET, request, GithubUserInfo.class).getBody();

    }
}

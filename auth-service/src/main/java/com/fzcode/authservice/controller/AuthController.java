package com.fzcode.authservice.controller;

import com.alibaba.fastjson.JSON;
import com.fzcode.authservice.dto.common.TokenDTO;
import com.fzcode.authservice.dto.request.*;
import com.fzcode.authservice.dto.response.GithubAccessToken;
import com.fzcode.authservice.dto.response.GithubUserInfo;
import com.fzcode.authservice.dto.response.SuccessResDTO;
import com.fzcode.authservice.exception.CustomizeException;
import com.fzcode.authservice.flow.AuthFlow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController()
@RequestMapping(value = "/auth")
public class AuthController {

    private AuthFlow authFlow;

    @Autowired
    public void setAuthFlow(AuthFlow authFlow) {
        this.authFlow = authFlow;
    }

    @Value("${auth.secret:N/A}")
    private String greeting;


    @GetMapping(value = "/test")
    public String test() {
        return greeting;
    }

    @PostMapping(value = "/login")
    public SuccessResDTO login(@RequestBody @Validated LoginDTO loginDTO) throws UsernameNotFoundException {
        String token = authFlow.login(loginDTO.getEmail(), loginDTO.getPassword());
        return new SuccessResDTO("登陆成功", new TokenDTO(token));
    }

    @PostMapping(value = "/register")
    public SuccessResDTO register(@RequestBody @Validated RegisterDTO registerDTO) throws CustomizeException {
        String token = authFlow.register(registerDTO.getEmail(), registerDTO.getPassword(), registerDTO.getCode(), registerDTO.getRegisterType());
        return new SuccessResDTO("创建成功", new TokenDTO(token));
    }

    @PostMapping(value = "/forget")
    public SuccessResDTO forget(@RequestBody @Validated ForgetDTO forgetDTO) throws CustomizeException {
        String email = authFlow.forget(forgetDTO.getEmail(), forgetDTO.getPassword(), forgetDTO.getCode());
        return new SuccessResDTO("修改成功", email);
    }

    @PutMapping(value = "/reset")
    public SuccessResDTO reset(@RequestBody @Validated ResetDTO resetDTO, @RequestHeader HttpHeaders httpHeaders) throws CustomizeException {
        String email = authFlow.reset(httpHeaders.getFirst("email"), resetDTO.getOldPassword(), resetDTO.getNewPassword());
        return new SuccessResDTO("修改成功", email);
    }

    @GetMapping(value = "/github")
    public String oauth2(@RequestParam(value = "code") String code, @RequestParam(value = "uHash") String uHash) throws CustomizeException {
//        System.out.println(principal.toString());
//        WebClient webClient = WebClient.create();
//        OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI + "/{registrationId}"。
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        GithubAccessToken githubAccessToken = restTemplate.postForObject(
                "https://github.com/login/oauth/access_token?client_id=1aeb61169dcca263aa3c&client_secret=9e9a8e8d52f0ea128bc123f1be769bee0c74d8aa&code=" + code,
                headers,
                GithubAccessToken.class
        );
        HttpHeaders accessTokenHeaders = new HttpHeaders();
        accessTokenHeaders.add("accept", "application/json");
        accessTokenHeaders.add("Authorization", "token " + githubAccessToken.getAccess_token());
        HttpEntity request = new HttpEntity(accessTokenHeaders);
        ResponseEntity<String> anotherExchange = restTemplate.exchange("https://api.github.com/user", HttpMethod.GET, request, String.class);
//        String userinfo = restTemplate.getForObject("https://api.github.com/user", accessTokenHeaders, String.class)
        GithubUserInfo githubUserInfo = JSON.parseObject(anotherExchange.getBody(), GithubUserInfo.class);
        if (githubUserInfo.getEmail() == null) {
            return "您的github尚未绑定邮箱，具体设置方法，右上角=>settings=>profile=>public email 选择您的邮箱";
        } else {
            // 这里偷懒了 直接用长度判断 token一定长
            String result = authFlow.githubRegister(githubUserInfo.getEmail(), githubUserInfo.getNode_id(), "1");
            if (result.length() > 15) {
                MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                map.add("uHash", uHash);
                map.add("token", result);
                HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
                String response = restTemplate.postForObject(
                        "http://192.168.31.150:9799/login",
                        httpEntity,
                        String.class
                );
                return "ddddd";
            }
            return result;
        }
//        return anotherExchange.getBody();

    }
}

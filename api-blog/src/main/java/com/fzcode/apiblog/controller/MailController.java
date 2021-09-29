package com.fzcode.apiblog.controller;

import com.fzcode.apiblog.config.Services;
import com.fzcode.internalcommon.dto.cloudmail.request.RegisterCodeRequest;
import com.fzcode.internalcommon.dto.http.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping(value = "/mail")
public class MailController {
    WebClient client;
    Services services;
    @Autowired
    public void setServices(Services services){
        this.services = services;
        client = WebClient.create(services.getCloud().getMail().getHost());
    }

    @PostMapping(value = "/register/code")
    public SuccessResponse getRegisterCode (@RequestBody @Validated RegisterCodeRequest registerCodeRequest){
        String result  = client.post()
                .uri("/register")
                .bodyValue(registerCodeRequest)
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
        return  new SuccessResponse("发送成功",result);
    }
}

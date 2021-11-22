package com.fzcode.apiblog.controller;

import com.fzcode.apiblog.config.Services;
import com.fzcode.internalcommon.dto.cloudmail.request.RegisterCodeRequest;
import com.fzcode.internalcommon.dto.http.SuccessResponse;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.internalcommon.http.Http;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "邮件模块")
@RestController
@RequestMapping(value = "/mail")
public class MailController {
    Http http;
    Services services;
    @Autowired
    public void setHttp(Http http){
        this.http = http;
    }
    @Autowired
    public void setServices(Services services){
        this.services = services;
    }

    @ApiOperation(value = "获取注册验证码")
    @PostMapping(value = "/register/code")
    public SuccessResponse getRegisterCode (@RequestBody @Validated RegisterCodeRequest registerCodeRequest) throws CustomizeException {
        String result =  http.post(services.getCloud().getMail().getHost()+"/register",registerCodeRequest, String.class);
        return  new SuccessResponse("发送成功",result);
    }
}

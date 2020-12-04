package com.fzcode.authservice.controller;

import com.alibaba.fastjson.JSON;
import com.fzcode.authservice.dto.pri.gate.GateReqDTO;
import com.fzcode.authservice.entity.Authorities;
import com.fzcode.authservice.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping(value = "/pri/authority")
public class AuthorityController {
    private AuthorityService authorityService;

    @Autowired
    public void setAuthorityService(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @PostMapping(value = "/get")
    public Authorities getAuth(@RequestBody GateReqDTO gateReqDTO) throws Exception {
        System.out.println("被请求了一次:" + gateReqDTO.getAccount());
        Authorities authorities = authorityService.findByAccount(gateReqDTO.getAccount());
        System.out.println(JSON.toJSONString(authorities));
        return authorities;
    }
}

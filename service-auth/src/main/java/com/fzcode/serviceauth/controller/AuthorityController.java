package com.fzcode.serviceauth.controller;

import com.alibaba.fastjson.JSON;
import com.fzcode.serviceauth.dto.pri.gate.GateReqDTO;
import com.fzcode.serviceauth.entity.Authorities;
import com.fzcode.serviceauth.dao.AuthorityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping(value = "/pri/authority",consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthorityController {
    private AuthorityDao authorityDao;

    @Autowired
    public void setAuthorityService(AuthorityDao authorityDao) {
        this.authorityDao = authorityDao;
    }

    @PostMapping(value = "/get")
    public Authorities getAuth(@RequestBody GateReqDTO gateReqDTO) {
        System.out.println("被请求了一次:" + gateReqDTO.getAccount());
        Authorities authorities = authorityDao.findByAccount(gateReqDTO.getAccount());
        System.out.println(JSON.toJSONString(authorities));
        return authorities;
    }
}

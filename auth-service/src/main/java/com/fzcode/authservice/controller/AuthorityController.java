package com.fzcode.authservice.controller;

import com.fzcode.authservice.entity.Authorities;
import com.fzcode.authservice.service.AuthorityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping(value = "/pri/authority")
public class AuthorityController {
    private AuthorityService authorityService;

    @PostMapping(value = "/get")
    public Authorities getAuth(String email) throws Exception {
        System.out.println("被请求了一次");
        return authorityService.findByAccount(email);
    }
}

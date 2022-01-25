package com.fzcode.serviceauth.controller;

import com.fzcode.serviceauth.entity.Users;
import com.fzcode.serviceauth.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户模块")
@RestController
@RequestMapping(value = "/user")
public class UserController {
    UserService userService;
    @Autowired
    public void  setUserService(UserService userService){
        this.userService = userService;
    }

    @GetMapping(value = "/get/{id}")
    public Users findByUid(@PathVariable(name = "id") String uid){
        return userService.findById(uid);
    }
}

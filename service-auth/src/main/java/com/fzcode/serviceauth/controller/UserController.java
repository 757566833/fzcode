package com.fzcode.serviceauth.controller;

import com.fzcode.internalcommon.dto.common.BatchGetDTO;
import com.fzcode.serviceauth.entity.Users;
import com.fzcode.serviceauth.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(value = "/get/list")
    public List<Users> findByUidIn(BatchGetDTO batchGetDTO){
        System.out.println(batchGetDTO.getIds());
        return userService.findByUidIn(batchGetDTO.getIds());
    }
}

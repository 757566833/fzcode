package com.fzcode.api.blog.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class AuthController {
    @PostMapping(value = "/test", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String test (){
        try {
            InetAddress ip4 = Inet4Address.getLocalHost();
            System.out.println();
            return ip4.getHostAddress();
        } catch (UnknownHostException e) {
            return "error";
        }

    }
}

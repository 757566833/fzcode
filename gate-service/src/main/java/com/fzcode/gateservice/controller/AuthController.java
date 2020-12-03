package com.fzcode.gateservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping(value = "/pri")
public class AuthController {
    @GetMapping("/refresh/authority")
    public void refreshAuthority() {

    }
}

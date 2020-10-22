package com.fzcode.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(value = "/auth")
public class CenterController {

    @GetMapping(value = "/test2")
    public String test() {
        return "test2";
    }
}

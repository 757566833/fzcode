package com.fzcode.authservice.controller;

import com.fzcode.authservice.dto.common.TokenDTO;
import com.fzcode.authservice.util.JwtUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/token")
public class TokenController {
    @PostMapping(value = "/parse")
    public String parseToken(@RequestBody TokenDTO tokenDTO) {
        System.out.println("解析token");
        return JwtUtils.parseToken(tokenDTO.getToken());
    }

}

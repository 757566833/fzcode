package com.fzcode.gateservice.controller;

import com.fzcode.gateservice.dto.common.AuthorityDTO;
import com.fzcode.gateservice.util.RedisUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping(value = "/pri")
public class AuthController {
    @GetMapping("/refresh/authority")
    public void refreshAuthority(@RequestBody AuthorityDTO authorityDTO) {
        RedisUtils.setHash("authority", authorityDTO.getAccount(), authorityDTO.getAuthority());
    }
}

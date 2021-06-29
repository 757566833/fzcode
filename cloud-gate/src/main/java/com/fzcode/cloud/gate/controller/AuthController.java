package com.fzcode.cloud.gate.controller;

import com.fzcode.cloud.gate.dto.common.AuthorityDTO;
import com.fzcode.cloud.gate.util.RedisUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping(value = "/pri",consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    @GetMapping(value = "/refresh/authority")
    public void refreshAuthority(@RequestBody AuthorityDTO authorityDTO) {
        RedisUtils.setHash("authority", authorityDTO.getAccount(), authorityDTO.getAuthority());
    }
}

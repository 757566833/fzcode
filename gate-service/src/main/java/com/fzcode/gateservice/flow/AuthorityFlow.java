package com.fzcode.gateservice.flow;

import com.alibaba.fastjson.JSON;
import com.fzcode.gateservice.dto.common.AuthorityDTO;
import com.fzcode.gateservice.http.Auth;
import com.fzcode.gateservice.util.RedisUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class AuthorityFlow {

    public Mono<String> saveAuthFromSqlToRedis(String email) {
        System.out.println("http");
        Mono<AuthorityDTO> authorityDTOMono = Auth.getAuthority(email);
        return authorityDTOMono.flatMap(authorityDTO -> RedisUtils.setHashReturnValue("authority", authorityDTO.getAccount(), authorityDTO.getAuthority()));
    }
}

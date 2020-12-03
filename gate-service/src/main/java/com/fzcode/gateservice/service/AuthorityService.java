package com.fzcode.gateservice.service;

import com.fzcode.gateservice.dto.common.AuthorityDTO;
import com.fzcode.gateservice.http.Auth;
import com.fzcode.gateservice.util.RedisUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthorityService {
    public Mono<String> saveAuthFromSqlToRedis(String email) {
        Mono<AuthorityDTO> authorityDTOMono = Auth.getAuthority(email);
        return authorityDTOMono.flatMap(authorityDTO -> RedisUtils.setHashReturnValue("authority", authorityDTO.getAccount(), authorityDTO.getAuthority()));
    }
}

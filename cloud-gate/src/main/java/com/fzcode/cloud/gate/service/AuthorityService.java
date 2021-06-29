package com.fzcode.cloud.gate.service;

import com.fzcode.cloud.gate.dto.common.AuthorityDTO;
import com.fzcode.cloud.gate.http.Auth;
import com.fzcode.cloud.gate.util.RedisUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthorityService {
    public Mono<String> saveAuthFromSqlToRedis(String email) {
        Mono<AuthorityDTO> authorityDTOMono = Auth.getAuthority(email);

        return authorityDTOMono.flatMap(authorityDTO -> {
            System.out.println("AuthorityService:"+authorityDTO);
            return RedisUtils.setHashReturnValue("authority", authorityDTO.getAccount(), authorityDTO.getAuthority());
        });
    }
}

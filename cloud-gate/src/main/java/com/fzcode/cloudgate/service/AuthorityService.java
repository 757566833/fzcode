package com.fzcode.cloudgate.service;

import com.fzcode.cloudgate.dto.common.AuthorityDTO;
import com.fzcode.cloudgate.http.Auth;
import com.fzcode.cloudgate.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthorityService {
    private Auth auth;
    @Autowired
    public void setAuth(Auth auth){
        this.auth = auth;
    }
    public Mono<String> saveAuthFromSqlToRedis(String email) {
        Mono<AuthorityDTO> authorityDTOMono = auth.getAuthority(email);

        return authorityDTOMono.flatMap(authorityDTO -> {
            System.out.println("AuthorityService:"+authorityDTO);
            return RedisUtils.setHashReturnValue("authority", authorityDTO.getAccount(), authorityDTO.getAuthority());
        });
    }
}

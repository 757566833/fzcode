package com.fzcode.cloudgate.dao;

import com.fzcode.cloudgate.http.Auth;
import com.fzcode.cloudgate.util.RedisUtils;
import com.fzcode.internalcommon.dto.common.AuthorityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthorityDao {
    private Auth auth;
    @Autowired
    public void setAuth(Auth auth){
        this.auth = auth;
    }
    public Mono<String> saveAuthFromSqlToRedis(String email) {
        Mono<AuthorityDTO> authorityDTOMono = auth.getAuthority(email);
        return authorityDTOMono.flatMap(authorityDTO -> RedisUtils.setHashReturnValue("authority", authorityDTO.getAccount(), authorityDTO.getAuthority()));
    }
}

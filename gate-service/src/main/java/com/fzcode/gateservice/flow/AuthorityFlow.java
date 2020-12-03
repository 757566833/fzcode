package com.fzcode.gateservice.flow;

import com.fzcode.gateservice.service.AuthorityService;
import com.fzcode.gateservice.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;



@Component
public class AuthorityFlow {
    AuthorityService authorityService;

    @Autowired
    public void setAuthorityService(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    public Mono<String> getAuthority(String email) {
        return RedisUtils.getHash("authority", email).flatMap(authority -> {
            if (authority != null) {
                return Mono.just(authority);
            } else {
                return authorityService.saveAuthFromSqlToRedis(email);
            }
        });
    }


//    public Mono<String> saveAuthFromSqlToRedis(String email) {
//        Mono<AuthorityDTO> authorityDTOMono = Auth.getAuthority(email);
//        return authorityDTOMono.flatMap(authorityDTO -> RedisUtils.setHashReturnValue("authority", authorityDTO.getAccount(), authorityDTO.getAuthority()));
//    }
}

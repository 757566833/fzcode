package com.fzcode.cloudgate.flow;

import com.fzcode.cloudgate.util.RedisUtils;
import com.fzcode.cloudgate.service.AuthorityService;
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
        System.out.println("getAuthority");
        return RedisUtils.getHash("authority",email).flatMap(authority -> {
            System.out.println("flatMap-redis获取的权限:" + authority);
            if (!authority.equals("")) {
                return Mono.just(authority);
            } else {
                System.out.println("没获取到:");
                return authorityService.saveAuthFromSqlToRedis(email);
            }
//            return null;
        });
//        Mono.create(monoSink -> {
//            RedisUtils.getHash("authority", email).subscribe(s -> {
//                System.out.println("subscribe-redis获取的权限:" + s);
//            });
//        }).subscribe(System.out::println);
//
////        Thread.sleep(5000);
//        return Mono.just("ADMIN");
    }


//    public Mono<String> saveAuthFromSqlToRedis(String email) {
//        Mono<AuthorityDTO> authorityDTOMono = Auth.getAuthority(email);
//        return authorityDTOMono.flatMap(authorityDTO -> RedisUtils.setHashReturnValue("authority", authorityDTO.getAccount(), authorityDTO.getAuthority()));
//    }
}

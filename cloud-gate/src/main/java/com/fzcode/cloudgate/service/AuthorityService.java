package com.fzcode.cloudgate.service;

import com.fzcode.cloudgate.util.RedisUtils;
import com.fzcode.cloudgate.dao.AuthorityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class AuthorityService {
    AuthorityDao authorityDao;

    @Autowired
    public void setAuthorityService(AuthorityDao authorityDao) {
        this.authorityDao = authorityDao;
    }

    public Mono<String> getAuthority(String email) {
        System.out.println("getAuthority");
        return RedisUtils.getHash("authority",email).flatMap(authority -> {
            System.out.println("flatMap-redis获取的权限:" + authority);
            if (!authority.equals("")) {
                return Mono.just(authority);
            } else {
                System.out.println("没获取到:");
                return authorityDao.saveAuthFromSqlToRedis(email);
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

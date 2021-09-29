package com.fzcode.cloudmail.handler;

import com.fzcode.cloudmail.util.CharUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class RedisHandler {
    @Autowired
    private ReactiveRedisTemplate<String,String> reactiveRedisTemplate;

    public Mono<Boolean> setString(String key,Integer timeout){
        String checkCode = CharUtil.getCharAndNumber(6);
        if(timeout>0){
            Duration duration =  Duration.ofMillis(timeout);
            return reactiveRedisTemplate.opsForValue().set(key,checkCode,duration);
        }else{
            return reactiveRedisTemplate.opsForValue().set(key,checkCode);
        }

    }
}

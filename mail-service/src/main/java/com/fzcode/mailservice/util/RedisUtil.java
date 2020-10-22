package com.fzcode.mailservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
@Component
public class RedisUtil {
    @Autowired
    private static ReactiveRedisTemplate<String,String> reactiveRedisTemplate;
    @Autowired
    public  void setReactiveRedisTemplate(ReactiveRedisTemplate<String, String> reactiveRedisTemplate) {
        RedisUtil.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    /**
     *
     * @param key
     * @param value
     * @param timeout 秒
     * @return
     */
    public static Mono<Boolean> setString(String key, String value, Integer timeout){
        Duration duration =  Duration.ofMillis(timeout);
        System.out.println("key:"+key+",value:"+value+",timeout:"+duration.toString());
        return reactiveRedisTemplate.opsForValue().set(key,value,duration);
    }
    public static Mono<Boolean> setString(String key, String value){
        return reactiveRedisTemplate.opsForValue().set(key,value);

    }
    public static Mono<String> getString(String key){
        return reactiveRedisTemplate.opsForValue().get(key);
    }
}

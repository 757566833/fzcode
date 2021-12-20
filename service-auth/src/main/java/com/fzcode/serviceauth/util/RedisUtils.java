package com.fzcode.serviceauth.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    private  RedisTemplate<String, String> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public  void setString(String key, String value, long time) {
        this.redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    public  void setString(String key, String value) {
        this.redisTemplate.opsForValue().set(key, value);
    }

    public  String getString(String key) {
        return this.redisTemplate.opsForValue().get(key);
    }

}
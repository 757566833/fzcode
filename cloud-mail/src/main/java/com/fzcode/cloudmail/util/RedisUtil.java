package com.fzcode.cloudmail.util;

import com.fzcode.internalcommon.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private static RedisTemplate<String, String> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public static void setString(String key, String value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    public static void setString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public static String getString(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public static void publishing(String msg){
        System.out.println("发送消息:"+msg);
        redisTemplate.convertAndSend(RedisConstant.channel,msg);
    }
}

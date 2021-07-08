package com.fzcode.serviceauth.util;

import com.fzcode.internalcommon.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
public class AuthRedisUtils {

    private static RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    public void  init(){
        LettuceConnectionFactory connectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
        connectionFactory.setDatabase(0);
        AuthRedisUtils.redisTemplate.setConnectionFactory(connectionFactory);
        connectionFactory.resetConnection();
    }

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
    public static void setHash(String hashName, String key, String value) {
         redisTemplate.opsForHash().put(hashName, key, value);
    }
}

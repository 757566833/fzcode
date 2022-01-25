package com.fzcode.cloudgate.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@Component
public class RedisUtils {
    private static ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    @Autowired
    public void setReactiveRedisTemplate(ReactiveRedisTemplate<String, String> reactiveRedisTemplate) {
        RedisUtils.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    /**
     * @param key
     * @param value
     * @param timeout 秒
     * @return
     */
    public static Mono<Boolean> setString(String key, String value, long timeout) {
        Duration duration = Duration.ofSeconds(timeout);
        System.out.println("key:" + key + ",value:" + value + ",timeout:" + duration.toString());
        return reactiveRedisTemplate.opsForValue().set(key, value, duration);
    }

    public static Mono<Boolean> setString(String key, String value) {
        return reactiveRedisTemplate.opsForValue().set(key, value);

    }

    public static Mono<String> getString(String key) {
        System.out.println("key:" + key);
        return reactiveRedisTemplate.hasKey(key).flatMap(aBoolean -> {
            if (aBoolean) {
                return reactiveRedisTemplate.opsForValue().get(key);
            } else {
                System.out.println("没有");
                return Mono.just("");
            }
        });
    }

    public static Mono<Boolean> setHash(String hashName, Map<String, String> value) {
        return reactiveRedisTemplate.opsForHash().putAll(hashName, value);
    }

    public static Mono<Boolean> setHash(String hashName, String key, String value) {
        return reactiveRedisTemplate.opsForHash().put(hashName, key, value);
    }

    public static Mono<String> setHashReturnValue(String hashName, String key, String value) {
        Mono<Boolean> mono = reactiveRedisTemplate.opsForHash().put(hashName, key, value);
        Mono<String> stringMono = mono.flatMap(aBoolean -> {
            if (aBoolean) {
                return Mono.just(value);
            } else {
                return Mono.just("");
            }
        });
        return stringMono;
    }

    public static Mono<String> getHash(String hashName, String key) {
        ReactiveHashOperations<String, String, String> reactiveHashOperations = reactiveRedisTemplate.opsForHash();
        return reactiveHashOperations.hasKey(hashName, key).flatMap(bool -> {
            if (bool) {
                return reactiveHashOperations.get(hashName, key);
            } else {
                return Mono.just("");
            }

        });
//        return reactiveHashOperations.get(hashName, key);
    }

}

package com.fzcode.cloudmail;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;

@SpringBootTest
class MailServiceApplicationTests {
    private static ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

//    @Autowired
//    public void setReactiveRedisTemplate(ReactiveRedisTemplate<String, String> reactiveRedisTemplate) {
//        MailServiceApplicationTests.reactiveRedisTemplate = reactiveRedisTemplate;
//    }
//    @Test
//    void contextLoads() {
////        ReactiveHashOperations<String, String, String> hashOps = reactiveRedisTemplate.opsForHash();
////        Mono mono1 = hashOps.put("apple", "x", "6000");
////        mono1.subscribe(System.out::println);
////        Mono mono2 = hashOps.put("apple", "xr", "5000");
////        mono2.subscribe(System.out::println);
////        Mono mono3 = hashOps.put("apple", "xs max", "8000");
////        mono3.subscribe(System.out::println);
////
////        ReactiveValueOperations<String, String> keyValue = reactiveRedisTemplate.opsForValue();
////        Mono mono4 = keyValue.set("apple2", "x");
////        mono4.subscribe(System.out::println);
//
//        Mono mono5 =RedisUtil.setString("apple4", "x");
//        mono5.subscribe(System.out::println);
//    }

}

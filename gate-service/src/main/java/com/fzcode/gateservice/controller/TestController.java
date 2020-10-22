//package com.examination.gateservice.controller;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Mono;
//
//import java.security.Principal;
//import java.util.Collections;
//import java.util.Map;
//
//@RestController
//public class TestController {
//    @GetMapping("/")
//    public Mono<Map<String,String>> test(Mono<Principal> principalMono){
//        return principalMono.map(Principal::getName).map(this::test2);
//    }
//
//    public Map<String,String> test2 (String username){
//        return Collections.singletonMap("message","hello "+ username);
//    }
//}

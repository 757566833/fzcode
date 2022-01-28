package com.fzcode.cloudmail;

import com.fzcode.internalcommon.utils.CopyUtils;
import com.fzcode.internalcommon.utils.JSONUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

@SpringBootTest
class MailServiceApplicationTests {


    @Test
    void contextLoads() {
//        ReactiveHashOperations<String, String, String> hashOps = reactiveRedisTemplate.opsForHash();
//        Mono mono1 = hashOps.put("apple", "x", "6000");
//        mono1.subscribe(System.out::println);
//        Mono mono2 = hashOps.put("apple", "xr", "5000");
//        mono2.subscribe(System.out::println);
//        Mono mono3 = hashOps.put("apple", "xs max", "8000");
//        mono3.subscribe(System.out::println);
//
//        ReactiveValueOperations<String, String> keyValue = reactiveRedisTemplate.opsForValue();
//        Mono mono4 = keyValue.set("apple2", "x");
//        mono4.subscribe(System.out::println);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("a","a");
        HashMap hashMap = new HashMap();
        BeanUtils.copyProperties(linkedHashMap,hashMap);
        final BeanWrapper src = new BeanWrapperImpl(linkedHashMap);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        System.out.println(JSONUtils.stringify(emptyNames.toArray(result)));
        System.out.println(JSONUtils.stringify(hashMap));

    }

}

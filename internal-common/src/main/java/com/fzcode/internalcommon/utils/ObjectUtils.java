package com.fzcode.internalcommon.utils;

import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ObjectUtils {
    public  static <T> Map<String, ?> object2Map(T bean)  {
        BeanMap beanMap = BeanMap.create(bean);
        Map<String, Object> map = new HashMap<>();

        beanMap.forEach((key, value) -> {
            map.put(String.valueOf(key), value);
        });
        return map;
    }
    public  static MultiValueMap<String, String> object2MultiValueMap(Object bean)  {
        BeanMap beanMap = BeanMap.create(bean);
        MultiValueMap map = new LinkedMultiValueMap();
        beanMap.forEach((key, value) -> {
            if(value instanceof String){
                map.add(String.valueOf(key), value);
            }else if(value instanceof Integer){
                map.add(String.valueOf(key), value.toString());
            }

        });
        return map;
    }

    public  static URI object2URI(String url, Object object)  {
        BeanMap beanMap = BeanMap.create(object);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        beanMap.forEach((key, value) -> {
            builder.queryParam( String.valueOf(key),  value);
        });
        return builder.build().encode().toUri();
    }
}

package com.fzcode.internalcommon.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fzcode.internalcommon.dto.common.Users;

public class JSONUtils {
    private static ObjectMapper objectMapper = new ObjectMapper()
            // 据说是解析arraylist用的
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
            // 遇到未知的属性跳过
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
            // 遇到空的bean不报错
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);

    public static <T> T parse (String content, Class<T> valueType){
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {
            //字符串转Json对象
            return objectMapper.readValue(content, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public static String stringify(Object object) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {
            //Json对象转为String字符串
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}

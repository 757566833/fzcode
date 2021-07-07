package com.fzcode.servicenote.utils;

import org.springframework.beans.BeanUtils;

import java.util.*;

public class ListUtils {
    public static <T> List<T>  copyList(Object obj, List<T> list2, Class<T> classObj) {
        if ((!Objects.isNull(obj)) && (!Objects.isNull(list2))) {
            List list1 = (List) obj;
            list1.forEach(item -> {
                try {
                    T data = classObj.newInstance();
                    BeanUtils.copyProperties(item, data);
                    list2.add(data);
                } catch (InstantiationException e) {
                } catch (IllegalAccessException e) {
                }


            });
            return list2;
        }else{
            return new ArrayList<>();
        }
    }
}

package com.fzcode.service.note.utils;

import com.fzcode.service.note.dto.response.ListResDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.domain.Page;

import java.util.*;

public class ListUtils {
    public static <T> Map<String, ?> bean2Map(T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        Map<String, Object> map = new HashMap<>();

        beanMap.forEach((key, value) -> {
            map.put(String.valueOf(key), value);
        });
        return map;
    }

    public static <T> ListResDTO pageList2ResList(Page<T> pageList) {
        List<T> list = pageList.getContent();
        Integer page = pageList.getPageable().getPageNumber();
        long count = pageList.getTotalElements();
        Integer size = pageList.getPageable().getPageSize();
        ListResDTO listResDTO = new ListResDTO<T>();
        listResDTO.setCount((int) count);
        listResDTO.setList(list);
        listResDTO.setPage(page);
        listResDTO.setSize(size);
        return listResDTO;
    }
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

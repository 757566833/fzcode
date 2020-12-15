package com.fzcode.noteservice.utils;

import com.fzcode.noteservice.dto.response.ListResDTO;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}

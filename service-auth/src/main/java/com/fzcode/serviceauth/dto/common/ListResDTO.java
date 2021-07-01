package com.fzcode.serviceauth.dto.common;

import lombok.Data;

@Data
public class ListResDTO<R> {
    private Object count;
    private Integer page;
    private Integer pageSize;
    private R list;
}

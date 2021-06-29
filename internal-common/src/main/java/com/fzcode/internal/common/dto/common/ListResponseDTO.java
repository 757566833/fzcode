package com.fzcode.internal.common.dto.common;
import lombok.Data;
public class ListResponseDTO<R> {
    private Object count;
    private Integer page;
    private Integer pageSize;
    private R list;
}

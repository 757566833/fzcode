package com.fzcode.internalcommon.dto.common;

import lombok.Data;

@Data
public class ListResponseDTO<R> {
    private Object count;
    private Integer page;
    private Integer pageSize;
    private R list;
}

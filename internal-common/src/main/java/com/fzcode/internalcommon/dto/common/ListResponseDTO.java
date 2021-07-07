package com.fzcode.internalcommon.dto.common;

import lombok.Data;

import java.util.List;

@Data
public class ListResponseDTO<R> {
    private Integer count;
    private Integer page;
    private Integer pageSize;
    private List<R> list;
}

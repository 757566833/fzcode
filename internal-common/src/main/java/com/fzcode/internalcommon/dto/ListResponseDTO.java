package com.fzcode.internalcommon.dto;

public class ListResponseDTO<R> {
    private Object count;
    private Integer page;
    private Integer pageSize;
    private R list;
}

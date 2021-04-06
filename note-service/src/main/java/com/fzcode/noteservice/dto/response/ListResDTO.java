package com.fzcode.noteservice.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ListResDTO<T> {
    private Integer count;
    private List<T> list;
    private Integer page;
    private Integer size;

}

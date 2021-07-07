package com.fzcode.internalcommon.dto.servicenote.response.text;

import lombok.Data;

import java.util.List;

@Data
public class TextResponse {
    private Integer Tid;
    private String title;
    private String description;
    private String text;
    private List<Integer> categories;
    private String username;
    private Integer is_delete;
    private String update_time;
    private Integer top;
    private Integer uid;
    private Integer create_by;
}

package com.fzcode.noteservice.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class TextGetResDTO {
    private String avatar;
    private Integer cid;
    private Integer create_by;
    private String create_time;
    private String category;
    private String description;
    private List<String> tags;
    private String text;
    private Integer tid;
    private String title;
    private Integer update_by;
    private String update_time;
    private String username;
}

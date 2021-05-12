package com.fzcode.noteservice.dto.response;

import com.fzcode.noteservice.repositroy.dbInterface.text.TextDBFindList;
import lombok.Data;

import java.util.List;

/**
 * 首页返回的列表
 */
@Data
public class IndexTextListResDTO implements TextDBFindList {
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

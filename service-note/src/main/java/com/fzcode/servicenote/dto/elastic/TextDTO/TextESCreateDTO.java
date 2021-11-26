package com.fzcode.servicenote.dto.elastic.TextDTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
public class TextESCreateDTO {
    @NotEmpty
    private Integer id;
    @NotEmpty
    private String title;
    private String content;
    private List<Integer> categories;
    private Date createTime;
    private Date updateTime;
    private Integer createBy;
    private String summary;

    public TextESCreateDTO(@NotEmpty Integer id, @NotEmpty String title) {
        this.id = id;
        this.title = title;
    }



}

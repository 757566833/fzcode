package com.fzcode.service.note.dto.request.Text;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class TextReqCreateDTO {

    @NotEmpty(message = "标题不能为空")
    private String title;
    private String description;
    @NotEmpty(message = "标题不能为空")
    private String raw;
    @NotEmpty(message = "标题不能为空")
    private String html;
    @NotNull(message = "分类")
    private List<Integer> categories;
}

package com.fzcode.noteservice.dto.request.Category;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CateGoryReqCreateDTO {

    @NotEmpty(message = "标题不能为空")
    private String title;
    @NotEmpty(message = "备注不能为空")
    private String description;
}

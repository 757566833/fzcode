package com.fzcode.noteservice.dto.request.Column;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ColumnReqCreateDTO {

    @NotEmpty(message = "标题不能为空")
    private String title;
    @NotEmpty(message = "备注不能为空")
    private String description;
    @NotEmpty(message = "logo不能为空")
    private String img;
    @NotEmpty(message = "详情不能为空")
    private String detail;

}

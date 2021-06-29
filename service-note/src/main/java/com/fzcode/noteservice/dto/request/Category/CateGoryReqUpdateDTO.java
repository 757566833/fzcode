package com.fzcode.noteservice.dto.request.Category;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CateGoryReqUpdateDTO {

    @NotNull(message = "cid不能为空")
    private Integer cid;
    @NotEmpty(message = "标题不能为空")
    private String title;
    @NotEmpty(message = "备注不能为空")
    private String description;
    @NotEmpty(message = "logo不能为空")
    private String img;
    @NotEmpty(message = "详情不能为空")
    private String detail;
}

package com.fzcode.noteservice.dto.request.Column;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ColumnReqPatchDTO {

    @NotEmpty(message = "cid不能为空")
    private Integer cid;
    private String title;
    private String description;
    private String img;
    private String detail;
}

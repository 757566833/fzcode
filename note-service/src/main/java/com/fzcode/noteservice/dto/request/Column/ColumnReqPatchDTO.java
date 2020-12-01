package com.fzcode.noteservice.dto.request.Column;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ColumnReqPatchDTO {

    @NotNull(message = "cid不能为空")
    private Integer cid;
    private String title;
    private String description;
    private String img;
    private String detail;
}

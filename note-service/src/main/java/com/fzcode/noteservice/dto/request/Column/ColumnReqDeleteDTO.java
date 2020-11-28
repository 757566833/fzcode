package com.fzcode.noteservice.dto.request.Column;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ColumnReqDeleteDTO {
    @NotEmpty(message = "cid不能为空")
    private Integer cid;
}

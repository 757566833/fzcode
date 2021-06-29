package com.fzcode.noteservice.dto.request.Category;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CateGoryReqDeleteDTO {
    @NotNull(message = "cid不能为空")
    private Integer cid;
}

package com.fzcode.service.note.dto.request.Category;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CateGoryReqPatchDTO {

    @NotNull(message = "cid不能为空")
    private Integer cid;
    private String title;
    private String description;
    private String img;
    private String detail;
}

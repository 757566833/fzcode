package com.fzcode.internalcommon.dto.servicenote.request.category;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CategoryPatchRequest {
    @NotNull(message = "cid不能为空")
    private Integer cid;
    private String title;
    private String description;
    private String img;
    private String detail;
}

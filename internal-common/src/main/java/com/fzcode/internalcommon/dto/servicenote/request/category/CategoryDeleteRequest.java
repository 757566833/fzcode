package com.fzcode.internalcommon.dto.servicenote.request.category;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CategoryDeleteRequest {
    @NotNull
    private Integer cid;
}

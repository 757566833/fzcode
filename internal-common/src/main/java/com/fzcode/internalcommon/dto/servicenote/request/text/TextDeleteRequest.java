package com.fzcode.internalcommon.dto.servicenote.request.text;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TextDeleteRequest {
    @NotEmpty
    private Integer tid;
}

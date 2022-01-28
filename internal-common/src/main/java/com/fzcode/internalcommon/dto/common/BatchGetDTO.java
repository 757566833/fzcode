package com.fzcode.internalcommon.dto.common;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BatchGetDTO {
    @NotBlank(message = "ids不可为空")
    private  String ids;
}

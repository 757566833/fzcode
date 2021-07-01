package com.fzcode.internalCommon.common;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PageDTO {
    @NotNull(message = "页码不能为空")
    private Integer page;
    @NotNull(message = "页长不能为空")
    private Integer pageSize;

    private String desc;
    private String asc;
}

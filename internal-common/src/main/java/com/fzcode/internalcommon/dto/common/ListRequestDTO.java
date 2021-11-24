package com.fzcode.internalcommon.dto.common;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ListRequestDTO {
    private Integer page = 1;
    private Integer pageSize=10;

    private String desc;
    private String asc;
}

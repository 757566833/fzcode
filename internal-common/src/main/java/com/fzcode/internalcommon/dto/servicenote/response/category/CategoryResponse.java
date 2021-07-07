package com.fzcode.internalcommon.dto.servicenote.response.category;

import lombok.Data;

import java.util.Date;

@Data
public class CategoryResponse {

    private int cid;
    private Date createTime;
    private Date updateTime;
    private Integer createBy;
    private Integer updateBy;
    private String title;
    private String description;
    private Integer isDelete = 0;
    private Integer deleteBy;
}

package com.fzcode.internalcommon.dto.http;

import lombok.Data;

@Data
public class SuccessResponse {
    private Integer code = 100000;
    private String msg;

    private Object data;
    public  SuccessResponse(String msg,Object data){
        this.msg=msg;
        this.data = data;
    }
}

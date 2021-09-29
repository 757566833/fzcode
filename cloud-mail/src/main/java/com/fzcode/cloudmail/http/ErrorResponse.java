package com.fzcode.cloudmail.http;

import lombok.Data;
import org.springframework.util.Assert;

import java.io.Serializable;

@Data
public class ErrorResponse<T> implements Serializable {
    public static Integer CODE_SUCCESS = 0;
    private Integer code;
    private String message;
    private T data;

    public static <T> ErrorResponse<T> error(ErrorResponse<?> response){
        return error(response.getCode(),response.getMessage());
    }
    public static <T> ErrorResponse<T> error(Integer code ,String message){
        Assert.isTrue(!CODE_SUCCESS.equals(code),"code must be error");
        ErrorResponse<T> response = new ErrorResponse<>();
        response.code= code;
        response.message=message;
        return response;
    }
}

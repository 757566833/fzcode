package com.fzcode.mailservice.http;

import org.springframework.util.Assert;

import java.io.Serializable;

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
//        response
        return response;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}

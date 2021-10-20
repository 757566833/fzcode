package com.fzcode.cloudmail.handler;

import com.fzcode.cloudmail.http.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

@ControllerAdvice
public class AsyncCustomGlobalExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(WebExchangeBindException.class)
    public ErrorResponse serverWeb(WebExchangeBindException webExchangeBindException){
//        System.out.println("---------");
//        System.out.println(webExchangeBindException.getAllErrors().toString());
//        System.out.println(webExchangeBindException.getMessage());
//        System.out.println(webExchangeBindException.getObjectName());
//        System.out.println(webExchangeBindException.getStatus());
//        System.out.println(webExchangeBindException.getReason().toString());
//        System.out.println(webExchangeBindException.getLocalizedMessage().toString());
//        System.out.println(webExchangeBindException.getSuppressedFields().toString());
//        System.out.println(webExchangeBindException.getModel().toString());
//        System.out.println(webExchangeBindException);
//        bind
        String errMessage = "";
//        System.out.println("---------");
        for (ObjectError error:
        webExchangeBindException.getAllErrors()) {
//            System.out.println(error.getObjectName());
//            System.out.println(error.getArguments().toString());
//            System.out.println(error.getCode());
//            System.out.println(error.getCodes().toString());
//            System.out.println(error.getDefaultMessage());
            errMessage+=error.getDefaultMessage()+";";
        }
        return  ErrorResponse.error(1 ,errMessage);
    }
}

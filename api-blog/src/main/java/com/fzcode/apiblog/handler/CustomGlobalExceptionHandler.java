package com.fzcode.apiblog.handler;


import com.fzcode.apiblog.exception.CustomizeException;
import com.fzcode.internalcommon.dto.http.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class CustomGlobalExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse requestParamsNotEmpty(MethodArgumentNotValidException methodArgumentNotValidException) {
        String errMessage = "";
        List<ObjectError> objectErrors = methodArgumentNotValidException.getBindingResult().getAllErrors();
        for (ObjectError o : objectErrors) {
            errMessage += o.getDefaultMessage()+";";
        }
        return new ErrorResponse(400, errMessage);
    }

    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    @ExceptionHandler(CustomizeException.class)
    public ErrorResponse customize(CustomizeException customizeException) {
        return new ErrorResponse(400, customizeException.getMessage());
    }

//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ErrorResDTO usernameNotFound(UsernameNotFoundException usernameNotFoundException) {
//        return new ErrorResDTO(400, usernameNotFoundException.getMessage());
//    }

}

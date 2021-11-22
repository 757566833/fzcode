package com.fzcode.apiblog.handler;

import com.fzcode.internalcommon.dto.http.ErrorResponse;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.internalcommon.utils.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class CustomGlobalExceptionHandler {
    /**
     * spring validation 的注释校验，对应的错误捕获
     * @param methodArgumentNotValidException
     * @return
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse requestParamsNotEmpty(MethodArgumentNotValidException methodArgumentNotValidException) {
        return ExceptionUtils.getMethodArgumentNotValidExceptionErrorMsg(methodArgumentNotValidException);
    }

    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    @ExceptionHandler(CustomizeException.class)
    public ErrorResponse customize(CustomizeException customizeException) {
        return new ErrorResponse(customizeException.getStatus(), customizeException.getMessage());
    }

//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ErrorResDTO usernameNotFound(UsernameNotFoundException usernameNotFoundException) {
//        return new ErrorResDTO(400, usernameNotFoundException.getMessage());
//    }

}

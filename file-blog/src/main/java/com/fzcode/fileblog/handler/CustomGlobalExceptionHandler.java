package com.fzcode.fileblog.handler;

import com.fzcode.internalcommon.dto.http.ErrorResponse;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.internalcommon.utils.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomGlobalExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse requestParamsNotEmpty(MethodArgumentNotValidException methodArgumentNotValidException) {
        return ExceptionUtils.getMethodArgumentNotValidExceptionErrorMsg(methodArgumentNotValidException);
    }

    @ExceptionHandler(CustomizeException.class)
    public ResponseEntity customize(CustomizeException customizeException) {
        return ExceptionUtils.getCustomizeExceptionErrorMsg(customizeException);
    }

//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ErrorResDTO usernameNotFound(UsernameNotFoundException usernameNotFoundException) {
//        return new ErrorResDTO(400, usernameNotFoundException.getMessage());
//    }

}

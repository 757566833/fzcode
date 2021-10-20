package com.fzcode.cloudmail.handler;

import com.fzcode.cloudmail.exception.CustomizeException;
import com.fzcode.internalcommon.dto.http.ErrorResponse;
import org.springframework.dao.DataAccessException;
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
    public com.fzcode.internalcommon.dto.http.ErrorResponse requestParamsNotEmpty(MethodArgumentNotValidException methodArgumentNotValidException) {
        String errMessage = "";
        List<ObjectError> objectErrors = methodArgumentNotValidException.getBindingResult().getAllErrors();
        for (ObjectError o : objectErrors) {
            errMessage += o.getDefaultMessage()+";";
        }
        return new com.fzcode.internalcommon.dto.http.ErrorResponse(400, errMessage);
    }

    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    @ExceptionHandler(CustomizeException.class)
    public com.fzcode.internalcommon.dto.http.ErrorResponse customize(CustomizeException customizeException) {
        return new com.fzcode.internalcommon.dto.http.ErrorResponse(400, customizeException.getMessage());
    }



    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(DataAccessException.class)
    public com.fzcode.internalcommon.dto.http.ErrorResponse dataAccess(DataAccessException dataAccessException) {
        return new ErrorResponse(400, dataAccessException.getMessage());
    }

}


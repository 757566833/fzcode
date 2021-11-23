package com.fzcode.internalcommon.utils;

import com.fzcode.internalcommon.dto.http.ErrorResponse;
import com.fzcode.internalcommon.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

public class ExceptionUtils {
    public static ErrorResponse getMethodArgumentNotValidExceptionErrorMsg(MethodArgumentNotValidException methodArgumentNotValidException){
        String errMessage = "";
        List<ObjectError> objectErrors = methodArgumentNotValidException.getBindingResult().getAllErrors();
        for (ObjectError o : objectErrors) {
            errMessage += o.getDefaultMessage()+";";
        }
        return new ErrorResponse(400, errMessage);
    }
    public static ResponseEntity getCustomizeExceptionErrorMsg(CustomizeException customizeException){
        HttpStatus status = customizeException.getStatus();
        ErrorResponse errorResponse = new ErrorResponse(status.value(), customizeException.getMessage());
        return new ResponseEntity(errorResponse, status);
    }

}

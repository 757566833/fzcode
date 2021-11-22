package com.fzcode.internalcommon.utils;

import com.fzcode.internalcommon.dto.http.ErrorResponse;
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
        return new ErrorResponse("400", errMessage);
    }
}

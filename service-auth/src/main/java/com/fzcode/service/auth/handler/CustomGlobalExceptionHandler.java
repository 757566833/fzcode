package com.fzcode.service.auth.handler;

import com.fzcode.service.auth.dto.response.ErrorResDTO;
import com.fzcode.service.auth.exception.CustomizeException;
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
    public ErrorResDTO requestParamsNotEmpty(MethodArgumentNotValidException methodArgumentNotValidException) {
        String errMessage = "";
        List<ObjectError> objectErrors = methodArgumentNotValidException.getBindingResult().getAllErrors();
        for (ObjectError o : objectErrors) {
            errMessage += o.getDefaultMessage()+";";
        }
        return new ErrorResDTO(400, errMessage);
    }

    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    @ExceptionHandler(CustomizeException.class)
    public ErrorResDTO customize(CustomizeException customizeException) {
        return new ErrorResDTO(400, customizeException.getMessage());
    }



    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(DataAccessException.class)
    public ErrorResDTO dataAccess(DataAccessException dataAccessException) {
        return new ErrorResDTO(400, dataAccessException.getMessage());
    }

}

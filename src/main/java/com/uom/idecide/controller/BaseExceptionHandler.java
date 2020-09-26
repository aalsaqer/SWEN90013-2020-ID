package com.uom.idecide.controller;
import com.uom.idecide.entity.Result;
import com.uom.idecide.entity.StatusCode;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handle all kinds of unhandled the exceptions
 */
@ControllerAdvice
public class BaseExceptionHandler {
	
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();        
        return new Result(false, StatusCode.ERROR, "Executing Error, are you using a expired token or wrong token?");
    }
}

package com.uom.idecide.controller;
import com.uom.idecide.entity.Result;
import com.uom.idecide.entity.StatusCode;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.NoSuchElementException;

/**
 * Handle all kinds of unhandled the exceptions
 */
@ControllerAdvice
public class BaseExceptionHandler {
	
    @ExceptionHandler(value = ExpiredJwtException.class)
    @ResponseBody
    public Result tokenExpired(Exception e){
        e.printStackTrace();        
        return new Result(false, StatusCode.TOKENEXPIRE, "Token expired");
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    @ResponseBody
    public Result noSuchElementException(Exception e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, "The key doesn't exist");
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, "Executing Error, are you using a expired token or wrong token?");
    }
}

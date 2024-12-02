package com.akichou.serial.exception;

import com.akichou.serial.response.CustomResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public CustomResponseEntity<Object> systemExceptionHandler(SystemException e){

        log.error("System exception occurred: {}", e.getMessage(), e) ;

        return CustomResponseEntity.errorEntity(e.getHttpCode(), e.getMessage()) ;
    }
}

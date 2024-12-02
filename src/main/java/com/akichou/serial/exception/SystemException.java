package com.akichou.serial.exception;

import com.akichou.serial.enumeration.HttpCodeEnum;
import lombok.Getter;

@Getter
public class SystemException extends RuntimeException {

    private final Integer httpCode ;
    private final String message ;

    public SystemException(HttpCodeEnum httpCodeEnum) {

        super(httpCodeEnum.getMessage()) ;

        this.httpCode = httpCodeEnum.getCode() ;
        this.message = httpCodeEnum.getMessage() ;
    }
}

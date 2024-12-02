package com.akichou.serial.response;

import com.akichou.serial.enumeration.HttpCodeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class CustomResponseEntity<T> implements Serializable {

    private Integer httpCode ;

    private String message ;

    private T data ;

    private CustomResponseEntity() {}

    // Specifics Constructors :
    private CustomResponseEntity(Integer httpCode, String message) {

        this.httpCode = httpCode ;
        this.message = message ;
    }

    private CustomResponseEntity(Integer httpCode, String message, T data) {

        this.httpCode = httpCode ;
        this.message = message ;
        this.data = data ;
    }

    // Builders :
    public CustomResponseEntity<T> ok(Integer httpCode, String message, T data) {

        this.httpCode = httpCode ;
        this.message = message ;
        this.data = data ;

        return this ;
    }

    public CustomResponseEntity<T> error(Integer httpCode, String message, T data) {

        this.httpCode = httpCode ;
        this.message = message ;
        this.data = data ;

        return this ;
    }

    // Result Entities :
    public static <T> CustomResponseEntity<T> okEntity() {

        CustomResponseEntity<T> entity = new CustomResponseEntity<>() ;

        return entity.ok(HttpCodeEnum.SUCCESS.getCode(), HttpCodeEnum.SUCCESS.getMessage(), null) ;
    }

    public static <T> CustomResponseEntity<T> okEntity(Integer httpCode, String message) {

        CustomResponseEntity<T> entity = new CustomResponseEntity<>() ;

        return entity.ok(httpCode, message, null) ;
    }

    public static <T> CustomResponseEntity<T> okEntity(Integer httpCode, String message, T data) {

        CustomResponseEntity<T> entity = new CustomResponseEntity<>() ;

        return entity.ok(httpCode, message, data) ;
    }

    public static <T> CustomResponseEntity<T> errorEntity() {

        CustomResponseEntity<T> entity = new CustomResponseEntity<>() ;

        return entity.error(HttpCodeEnum.SYSTEM_INTERNAL_ERROR.getCode(), HttpCodeEnum.SYSTEM_INTERNAL_ERROR.getMessage(), null) ;
    }

    public static <T> CustomResponseEntity<T> errorEntity(Integer httpCode, String message) {

        CustomResponseEntity<T> entity = new CustomResponseEntity<>() ;

        return entity.error(httpCode, message, null) ;
    }

    public static <T> CustomResponseEntity<T> errorEntity(Integer httpCode, String message, T data) {

        CustomResponseEntity<T> entity = new CustomResponseEntity<>() ;

        return entity.error(httpCode, message, data) ;
    }
}

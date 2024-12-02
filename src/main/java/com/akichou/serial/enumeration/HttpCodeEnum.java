package com.akichou.serial.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HttpCodeEnum {

    SUCCESS(200, "SUCCESS"),

    SYSTEM_INTERNAL_ERROR(500, "SYSTEM_INTERNAL_ERROR"),

    SERIALIZE_SUCCESS(601, "Serialization Successfully"),
    SERIALIZE_FAIL(602, "Serialization Failed"),
    DESERIALIZE_SUCCESS(603, "Deserialization Successfully"),
    DESERIALIZE_FAIL(604, "Deserialization Failed") ;

    final private Integer code ;
    final private String message ;
}

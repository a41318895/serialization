package com.akichou.serial.controller;

import com.akichou.serial.component.UserProducer;
import com.akichou.serial.entity.dto.JsonStringDto;
import com.akichou.serial.entity.dto.UserInfoDto;
import com.akichou.serial.entity.dto.UserRedisKeyDto;
import com.akichou.serial.entity.vo.FileNameVo;
import com.akichou.serial.entity.vo.JsonStringVo;
import com.akichou.serial.entity.vo.UserInfoVo;
import com.akichou.serial.entity.vo.UserRedisKeyVo;
import com.akichou.serial.enumeration.SerializationSerFileNamingEnum;
import com.akichou.serial.response.CustomResponseEntity;
import com.akichou.serial.service.SerialService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class SerialController {

    private final SerialService serialService ;
    private final UserProducer userProducer ;

    @PostMapping("/ser")
    public CustomResponseEntity<FileNameVo> serializeWithOutputStream(
            @Validated @RequestBody UserInfoDto userInfoDto,
            @RequestParam(required = false, defaultValue = "UUID") SerializationSerFileNamingEnum fileNamingEnum) {

        return serialService.serializeWithOutputStream(userInfoDto, fileNamingEnum) ;
    }

    @PostMapping("/deser")
    public CustomResponseEntity<UserInfoVo> deserializeWithOutputStream(@RequestParam String fileName) {

        return serialService.deserializeWithOutputStream(fileName) ;
    }

    @PostMapping("/jackson/ser")
    public CustomResponseEntity<JsonStringVo> serializeWithJackson(
            @Validated @RequestBody UserInfoDto userInfoDto) {

        return serialService.serializeWithJackson(userInfoDto) ;
    }

    @PostMapping("/jackson/deser")
    public CustomResponseEntity<UserInfoVo> deserializeWithJackson(
            @Validated @RequestBody JsonStringDto jsonStringDto) {

        return serialService.deserializeWithJackson(jsonStringDto) ;
    }

    @PostMapping("/redis/ser")
    public CustomResponseEntity<UserRedisKeyVo> serializeToRedis(
            @Validated @RequestBody UserInfoDto userInfoDto) {

        return serialService.serializeToRedis(userInfoDto) ;
    }

    @PostMapping("/redis/deser")
    public CustomResponseEntity<UserInfoVo> deserializeFromRedis(
            @Validated @RequestBody UserRedisKeyDto userRedisKeyDto) {

        return serialService.deserializeFromRedis(userRedisKeyDto) ;
    }

    @PostMapping("/rabbitmq")
    public void serializeWithRabbitMQ(
            @Validated @RequestBody UserInfoDto userInfoDto) {

        userProducer.produceMessage(userInfoDto) ;
    }
}

package com.akichou.serial.service;

import com.akichou.serial.entity.dto.JsonStringDto;
import com.akichou.serial.entity.dto.UserInfoDto;
import com.akichou.serial.entity.dto.UserRedisKeyDto;
import com.akichou.serial.entity.vo.FileNameVo;
import com.akichou.serial.entity.vo.JsonStringVo;
import com.akichou.serial.entity.vo.UserInfoVo;
import com.akichou.serial.entity.vo.UserRedisKeyVo;
import com.akichou.serial.enumeration.SerializationSerFileNamingEnum;
import com.akichou.serial.response.CustomResponseEntity;

public interface SerialService {

    CustomResponseEntity<FileNameVo> serializeWithOutputStream(UserInfoDto userInfoDto, SerializationSerFileNamingEnum fileNamingEnum) ;

    CustomResponseEntity<UserInfoVo> deserializeWithOutputStream(String fileName) ;

    CustomResponseEntity<JsonStringVo> serializeWithJackson(UserInfoDto userInfoDto);

    CustomResponseEntity<UserInfoVo> deserializeWithJackson(JsonStringDto jsonStringDto);

    CustomResponseEntity<UserRedisKeyVo> serializeToRedis(UserInfoDto userInfoDto);

    CustomResponseEntity<UserInfoVo> deserializeFromRedis(UserRedisKeyDto userRedisKeyDto);
}

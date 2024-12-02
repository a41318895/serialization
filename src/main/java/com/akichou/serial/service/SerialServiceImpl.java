package com.akichou.serial.service;

import com.akichou.serial.entity.User;
import com.akichou.serial.entity.dto.JsonStringDto;
import com.akichou.serial.entity.dto.UserInfoDto;
import com.akichou.serial.entity.dto.UserRedisKeyDto;
import com.akichou.serial.entity.vo.FileNameVo;
import com.akichou.serial.entity.vo.JsonStringVo;
import com.akichou.serial.entity.vo.UserInfoVo;
import com.akichou.serial.entity.vo.UserRedisKeyVo;
import com.akichou.serial.enumeration.HttpCodeEnum;
import com.akichou.serial.enumeration.SerializationSerFileNamingEnum;
import com.akichou.serial.exception.SystemException;
import com.akichou.serial.functionalInterface.DeserializationSupplier;
import com.akichou.serial.functionalInterface.SerializationSupplier;
import com.akichou.serial.response.CustomResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static com.akichou.serial.constant.SystemConstant.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SerialServiceImpl implements SerialService {

    @Value(value = "${ser.file_directory}")
    private String fileDirectory ;

    private final RedisTemplate<String, User> redisTemplate ;

    @Override
    public CustomResponseEntity<FileNameVo> serializeWithOutputStream(
            UserInfoDto userInfoDto, SerializationSerFileNamingEnum fileNamingEnum) {

        return executeSerializeOperation(() -> {

            String fileName = getFileOutputStreamFileName(fileNamingEnum) ;
            User user = new User(userInfoDto.name(), userInfoDto.age()) ;

            // Try-with-resource: Automatically closes the resources after the try block is finished.
            try (FileOutputStream fos = new FileOutputStream(fileName) ;
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {

                oos.writeObject(user) ;

                log.info(SERIALIZE_SUCCESS_MSG_WITH_FILENAME, fileName) ;

                return CustomResponseEntity.okEntity(
                        HttpCodeEnum.SERIALIZE_SUCCESS.getCode(),
                        HttpCodeEnum.SERIALIZE_SUCCESS.getMessage(),
                        new FileNameVo(fileName)) ;
            }
        }) ;
    }

    @Override
    public CustomResponseEntity<UserInfoVo> deserializeWithOutputStream(String fileName) {

        return executeDeserializeOperation(() -> {

            try (FileInputStream fis = new FileInputStream(fileName) ;
                 ObjectInputStream ois = new ObjectInputStream(fis)) {

                User user = (User) ois.readObject() ;

                logDeserializationSuccess() ;

                return CustomResponseEntity.okEntity(
                        HttpCodeEnum.DESERIALIZE_SUCCESS.getCode(),
                        HttpCodeEnum.DESERIALIZE_SUCCESS.getMessage(),
                        new UserInfoVo(user.getName(), user.getAge())
                ) ;
            }
        }) ;
    }

    @Override
    public CustomResponseEntity<JsonStringVo> serializeWithJackson(UserInfoDto userInfoDto) {

        User user = new User(userInfoDto.name(), userInfoDto.age()) ;

        return executeSerializeOperation(() -> {

            ObjectMapper mapper = new ObjectMapper() ;

            String jsonString = mapper.writeValueAsString(user) ;

            logSerializationSuccess() ;

            return CustomResponseEntity.okEntity(
                    HttpCodeEnum.SERIALIZE_SUCCESS.getCode(),
                    HttpCodeEnum.SERIALIZE_SUCCESS.getMessage(),
                    new JsonStringVo(jsonString)
            ) ;
        }) ;
    }

    @Override
    public CustomResponseEntity<UserInfoVo> deserializeWithJackson(JsonStringDto jsonStringDto) {

        return executeDeserializeOperation(() -> {

            ObjectMapper mapper = new ObjectMapper() ;

            User user = mapper.readValue(jsonStringDto.jsonString(), User.class) ;

            logDeserializationSuccess() ;

            return CustomResponseEntity.okEntity(
                    HttpCodeEnum.DESERIALIZE_SUCCESS.getCode(),
                    HttpCodeEnum.DESERIALIZE_SUCCESS.getMessage(),
                    new UserInfoVo(user.getName(), user.getAge())
            ) ;
        }) ;
    }

    @Override
    public CustomResponseEntity<UserRedisKeyVo> serializeToRedis(UserInfoDto userInfoDto) {

        User user = new User(userInfoDto.name(), userInfoDto.age()) ;

        redisTemplate.opsForValue().set("user:" + user.getName(), user) ;

        logSerializationSuccess() ;

        return CustomResponseEntity.okEntity(
                HttpCodeEnum.SERIALIZE_SUCCESS.getCode(),
                HttpCodeEnum.SERIALIZE_SUCCESS.getMessage(),
                new UserRedisKeyVo("user:" + user.getName())
        ) ;
    }

    @Override
    public CustomResponseEntity<UserInfoVo> deserializeFromRedis(UserRedisKeyDto userRedisKeyDto) {

        User user = redisTemplate.opsForValue().get(userRedisKeyDto.key()) ;
        if(user == null) throw new SystemException(HttpCodeEnum.DESERIALIZE_FAIL) ;

        logDeserializationSuccess() ;

        return CustomResponseEntity.okEntity(
                HttpCodeEnum.DESERIALIZE_SUCCESS.getCode(),
                HttpCodeEnum.DESERIALIZE_SUCCESS.getMessage(),
                new UserInfoVo(user.getName(), user.getAge())
        ) ;
    }

    // Exception Explicit Declaration Handling
    private static <T> T executeSerializeOperation(SerializationSupplier<T> supplier) {

        try {

            return supplier.doSerialize() ;
        } catch (IOException e) {

            log.error(e.getMessage()) ;

            throw new SystemException(HttpCodeEnum.SERIALIZE_FAIL) ;
        }
    }

    private static <T> T executeDeserializeOperation(DeserializationSupplier<T> supplier) {

        try {

            return supplier.doDeserialize() ;
        } catch (IOException | ClassNotFoundException e) {

            log.error(e.getMessage()) ;

            throw new SystemException(HttpCodeEnum.DESERIALIZE_FAIL) ;
        }
    }

    private String getFileOutputStreamFileName(SerializationSerFileNamingEnum fileNamingEnum) {

        String infix = switch (fileNamingEnum) {

            case UUID -> UUID.randomUUID().toString() ;

            case TIMESTAMP -> new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) ;
        } ;

        String directoryPath = fileDirectory ;
        File directory = new File(directoryPath) ;
        if (!directory.exists()) directory.mkdirs() ;

        String fileName = "user_" + infix + ".ser" ;

        return directoryPath + fileName ;
    }

    private void logSerializationSuccess() {

        log.info(SERIALIZE_SUCCESS_MSG) ;
    }

    private void logDeserializationSuccess() {

        log.info(DESERIALIZE_SUCCESS_MSG) ;
    }
}

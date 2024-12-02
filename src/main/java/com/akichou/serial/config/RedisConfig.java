package com.akichou.serial.config;

import com.akichou.serial.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, User> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, User> template = new RedisTemplate<>() ;

        Jackson2JsonRedisSerializer<User> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(User.class) ;

        template.setConnectionFactory(redisConnectionFactory) ;
        template.setValueSerializer(jackson2JsonRedisSerializer) ;

        return template ;
    }
}

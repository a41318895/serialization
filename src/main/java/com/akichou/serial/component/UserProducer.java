package com.akichou.serial.component;

import com.akichou.serial.entity.User;
import com.akichou.serial.entity.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserProducer {

    @Value("${rabbit_exchange_name}")
    private String exchangeName ;

    @Value("${rabbit_routing_key_name}")
    private String routingKeyName ;

    private final RabbitTemplate rabbitTemplate ;

    public void produceMessage(UserInfoDto userInfoDto) {

        User user = new User(userInfoDto.name(), userInfoDto.age()) ;

        rabbitTemplate.convertAndSend(exchangeName, routingKeyName, user) ;

        log.info("Message Produced Successfully") ;
    }
}

package com.akichou.serial.component;

import com.akichou.serial.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableRabbit
public class UserConsumer {

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue("${rabbit_queue_name}"),
                    exchange = @Exchange(
                            value = "${rabbit_exchange_name}",
                            type = ExchangeTypes.DIRECT
                    ),
                    key = "${rabbit_routing_key_name}"
            )
    )
    private void consumeFromQueue(@Payload User user) {

        log.info("Message Consumed Successfully") ;

        log.info("Consumed User [ NAME = {}, AGE = {}]", user.getName(), user.getAge()) ;
    }
}

package com.akichou.serial.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbit_queue_name}")
    private String queueName ;

    @Value("${rabbit_exchange_name}")
    private String exchangeName ;

    @Value("${rabbit_routing_key_name}")
    private String routingKeyName ;

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {

        return new Jackson2JsonMessageConverter() ;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory) ;

        rabbitTemplate.setMessageConverter(messageConverter()) ;

        return rabbitTemplate ;
    }

    @Bean
    public Queue queue() {

        return new Queue(queueName, true) ;
    }

    @Bean
    public DirectExchange directExchange() {

        return new DirectExchange(exchangeName, true, false) ;
    }

    @Bean
    public Binding binding() {

        return BindingBuilder.bind(queue()).to(directExchange()).with(routingKeyName) ;
    }
}

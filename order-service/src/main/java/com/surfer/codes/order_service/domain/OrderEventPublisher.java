package com.surfer.codes.order_service.domain;

import com.surfer.codes.order_service.ApplicationProperties;
import com.surfer.codes.order_service.domain.models.CreateOrderEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ApplicationProperties properties;

    void publish(CreateOrderEvent orderEvent) {
        rabbitTemplate.convertAndSend(properties.orderEventsExchange(), properties.newOrdersQueue(), orderEvent);
    }
}

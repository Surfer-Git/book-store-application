package com.surfer.codes.order_service.domain;

import com.surfer.codes.order_service.ApplicationProperties;
import com.surfer.codes.order_service.domain.models.CancelledOrderEvent;
import com.surfer.codes.order_service.domain.models.CreateOrderEvent;
import com.surfer.codes.order_service.domain.models.DeliveredOrderEvent;
import com.surfer.codes.order_service.domain.models.ErrorOrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {
    private final Logger log = LoggerFactory.getLogger(OrderEventPublisher.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ApplicationProperties properties;

    void publish(CreateOrderEvent orderEvent) {
        log.info("OrderEventId = {} | Publishing Event to {}", orderEvent.eventId(), properties.newOrdersQueue());
        rabbitTemplate.convertAndSend(properties.orderEventsExchange(), properties.newOrdersQueue(), orderEvent);
    }

    void publish(DeliveredOrderEvent orderEvent) {
        log.info("OrderEventId = {} | Publishing Event to {}", orderEvent.eventId(), properties.deliveredOrdersQueue());
        rabbitTemplate.convertAndSend(properties.orderEventsExchange(), properties.deliveredOrdersQueue(), orderEvent);
    }

    void publish(CancelledOrderEvent orderEvent) {
        log.info("OrderEventId = {} | Publishing Event to {}", orderEvent.eventId(), properties.cancelledOrdersQueue());
        rabbitTemplate.convertAndSend(properties.orderEventsExchange(), properties.cancelledOrdersQueue(), orderEvent);
    }

    void publish(ErrorOrderEvent orderEvent) {
        log.info("OrderEventId = {} | Publishing Event to {}", orderEvent.eventId(), properties.errorOrdersQueue());
        rabbitTemplate.convertAndSend(properties.orderEventsExchange(), properties.errorOrdersQueue(), orderEvent);
    }
}

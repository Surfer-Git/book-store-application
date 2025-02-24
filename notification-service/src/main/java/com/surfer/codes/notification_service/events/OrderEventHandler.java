package com.surfer.codes.notification_service.events;

import com.surfer.codes.notification_service.events.models.CreateOrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventHandler {

    private Logger log = LoggerFactory.getLogger(OrderEventHandler.class);

    @RabbitListener(queues = "${notification.new-orders-queue}")
    void orderCreatedEventHandler(CreateOrderEvent event) {
        log.info("order-number = {} | order created", event.orderNumber());
    }
}

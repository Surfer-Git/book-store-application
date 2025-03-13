package com.surfer.codes.notification_service.events;

import com.surfer.codes.notification_service.domain.NotificationService;
import com.surfer.codes.notification_service.events.models.CancelledOrderEvent;
import com.surfer.codes.notification_service.events.models.CreateOrderEvent;
import com.surfer.codes.notification_service.events.models.DeliveredOrderEvent;
import com.surfer.codes.notification_service.events.models.ErrorOrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventHandler {

    private Logger log = LoggerFactory.getLogger(OrderEventHandler.class);
    private final NotificationService notificationService;

    public OrderEventHandler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "${notification.new-orders-queue}")
    void orderCreatedEventHandler(CreateOrderEvent event) {
        log.info("order-number = {} | order created", event.orderNumber());
        notificationService.sendOrderEventNotification(event);
    }

    @RabbitListener(queues = "${notification.delivered-orders-queue}")
    void orderDeliveredEventHandler(DeliveredOrderEvent event) {
        log.info("order-number = {} | order delivered", event.orderNumber());
        notificationService.sendOrderEventNotification(event);
    }

    @RabbitListener(queues = "${notification.cancelled-orders-queue}")
    void orderCancelledEventHandler(CancelledOrderEvent event) {
        log.info("order-number = {} | order cancelled", event.orderNumber());
        notificationService.sendOrderEventNotification(event);
    }

    @RabbitListener(queues = "${notification.error-orders-queue}")
    void orderErrorEventHandler(ErrorOrderEvent event) {
        log.info("order-number = {} | order error", event.orderNumber());
        notificationService.sendOrderEventNotification(event);
    }
}

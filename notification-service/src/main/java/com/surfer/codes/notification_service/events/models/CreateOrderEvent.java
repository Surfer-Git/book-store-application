package com.surfer.codes.notification_service.events.models;

import java.time.LocalDateTime;
import java.util.Set;

public record CreateOrderEvent(
        String eventId,
        String orderNumber,
        Customer customer,
        OrderEventType orderEventType,
        Set<OrderItem> orderItems,
        LocalDateTime createdAt) {}

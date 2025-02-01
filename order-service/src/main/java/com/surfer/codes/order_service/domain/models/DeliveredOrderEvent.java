package com.surfer.codes.order_service.domain.models;

import java.time.LocalDateTime;
import java.util.Set;

public record DeliveredOrderEvent(
        String eventId,
        String orderNumber,
        Customer customer,
        OrderEventType orderEventType,
        Set<OrderItem> orderItems,
        LocalDateTime updatedAt) {}

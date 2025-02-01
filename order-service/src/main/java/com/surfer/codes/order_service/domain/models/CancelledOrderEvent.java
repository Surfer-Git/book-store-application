package com.surfer.codes.order_service.domain.models;

import java.time.LocalDateTime;
import java.util.Set;

public record CancelledOrderEvent(
        String eventId,
        String orderNumber,
        Customer customer,
        OrderEventType orderEventType,
        String reason,
        Set<OrderItem> orderItems,
        LocalDateTime updatedAt) {}

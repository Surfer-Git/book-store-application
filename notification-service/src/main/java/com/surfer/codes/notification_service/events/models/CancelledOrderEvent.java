package com.surfer.codes.notification_service.events.models;

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

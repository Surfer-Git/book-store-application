package com.surfer.codes.order_service.domain.models;

import java.time.LocalDateTime;

public record OrderSummary(String orderNumber, OrderStatus status, LocalDateTime updatedAt) {}

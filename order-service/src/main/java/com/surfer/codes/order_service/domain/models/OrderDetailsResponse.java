package com.surfer.codes.order_service.domain.models;

import java.math.BigDecimal;
import java.util.Set;

public record OrderDetailsResponse(
        String orderNumber,
        OrderStatus orderStatus,
        String userName,
        Customer customer,
        Address deliveryAddress,
        Set<OrderItem> orderItems,
        BigDecimal totalAmount,
        String comments) {}

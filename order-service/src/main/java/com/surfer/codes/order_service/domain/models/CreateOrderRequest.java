package com.surfer.codes.order_service.domain.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record CreateOrderRequest(
        @Valid Customer customer, @Valid Address deliveryAddress, @NotEmpty @Valid Set<OrderItem> items) {}

package com.surfer.codes.notification_service.events.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record OrderItem(
        @NotBlank(message = "code is required") String code,
        @NotBlank(message = "name is required") String name,
        @NotNull(message = "price is required") BigDecimal price,
        @NotNull(message = "quantity is required") @Min(1) Integer quantity) {}

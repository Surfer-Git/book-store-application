package com.surfer.codes.order_service.client;

import java.math.BigDecimal;

public record Product(String name, String code, String description, String imageUrl, BigDecimal price) {}

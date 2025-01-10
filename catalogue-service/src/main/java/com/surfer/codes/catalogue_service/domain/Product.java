package com.surfer.codes.catalogue_service.domain;

import java.math.BigDecimal;

public record Product(String name, String code, String description, String imageUrl, BigDecimal price) {}

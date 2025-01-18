package com.surfer.codes.order_service.domain.models;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public record Customer(@NotBlank String name, @NotBlank @Email String email, @NotBlank String phone) {}

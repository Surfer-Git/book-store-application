package com.surfer.codes.notification_service.events.models;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public record Customer(@NotBlank String name, @NotBlank @Email String email, @NotBlank String phone) {}

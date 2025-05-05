package com.surfer.codes.webApp;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bookstore")
public record ApplicationProperties(String apiGatewayUrl) {}

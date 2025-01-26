package com.surfer.codes.order_service.client;

import java.util.Optional;

import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CatalogueServiceClient {

    private final Logger log = LoggerFactory.getLogger(CatalogueServiceClient.class);

    @Autowired
    RestClient catalogueRestClient;

    @Retry(name = "catalogue-service")
    public Optional<Product> getProductByCode(String code) {
        Product product = catalogueRestClient
                .get()
                .uri("/api/products/{code}", code)
                .retrieve()
                .body(Product.class);
        return Optional.ofNullable(product);
    }
}

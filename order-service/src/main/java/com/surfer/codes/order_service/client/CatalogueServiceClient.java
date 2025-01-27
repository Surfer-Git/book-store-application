package com.surfer.codes.order_service.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.Optional;
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

    @CircuitBreaker(name = "catalogue-service")
    @Retry(name = "catalogue-service")
    public Optional<Product> getProductByCode(String code) {
        log.info("Fetching product for code : {}", code);
        Product product = catalogueRestClient
                .get()
                .uri("/api/products/{code}", code)
                .retrieve()
                .body(Product.class);
        return Optional.ofNullable(product);
    }
}

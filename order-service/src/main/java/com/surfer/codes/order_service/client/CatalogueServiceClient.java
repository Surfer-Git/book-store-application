package com.surfer.codes.order_service.client;

import java.util.Optional;
import org.apache.commons.lang3.exception.ExceptionUtils;
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

    public Optional<Product> getProductByCode(String code) {
        Product product = null;
        try {
            product = catalogueRestClient
                    .get()
                    .uri("/api/products/{code}", code)
                    .retrieve()
                    .body(Product.class);
        } catch (Exception e) {
            log.error("Error from catalogue: {}", ExceptionUtils.getStackTrace(e));
        }
        return Optional.ofNullable(product);
    }
}

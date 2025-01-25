package com.surfer.codes.order_service.domain;

import com.surfer.codes.order_service.client.CatalogueServiceClient;
import com.surfer.codes.order_service.client.Product;
import com.surfer.codes.order_service.domain.models.CreateOrderRequest;
import org.springframework.stereotype.Component;

@Component
class OrderValidator {

    private final CatalogueServiceClient catalogueServiceClient;

    OrderValidator(CatalogueServiceClient catalogueServiceClient) {
        this.catalogueServiceClient = catalogueServiceClient;
    }

    void validate(CreateOrderRequest createOrderRequest) {
        createOrderRequest.items().forEach(item -> {
            Product product = catalogueServiceClient
                    .getProductByCode(item.code())
                    .orElseThrow(() -> new InvalidOrderException("Invalid Product Code: " + item.code()));

            if (product.price().compareTo(item.price()) != 0) {
                throw new InvalidOrderException("Product price not matching");
            }
        });
    }
}

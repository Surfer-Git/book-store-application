package com.surfer.codes.webApp.clients.catalog;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface CatalogServiceClient {

    @GetExchange("/catalogue/api/products")
    PagedResult<Product> getProducts(@RequestParam("pageNo") int pageNo);

    @GetExchange("/catalogue/api/products/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable String code);
}

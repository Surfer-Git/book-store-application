package com.surfer.codes.catalogue_service.web.controllers;

import com.surfer.codes.catalogue_service.domain.PagedResult;
import com.surfer.codes.catalogue_service.domain.Product;
import com.surfer.codes.catalogue_service.domain.ProductNotFoundException;
import com.surfer.codes.catalogue_service.domain.ProductService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
class ProductController {
    private final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    PagedResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") Integer pageNo) {
        log.info("Controller call to getProducts at page: {}", pageNo);
        return productService.getProducts(pageNo);
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> getProuductByCode(@PathVariable String code) {
        log.info("Controller call to getProductsByCode: {}", code);
        Optional<Product> product = productService.getProductByCode(code);
        return product.map(ResponseEntity::ok).orElseThrow(() -> ProductNotFoundException.forCode(code));
    }
}

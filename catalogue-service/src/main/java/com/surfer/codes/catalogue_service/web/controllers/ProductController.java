package com.surfer.codes.catalogue_service.web.controllers;

import com.surfer.codes.catalogue_service.domain.PagedResult;
import com.surfer.codes.catalogue_service.domain.Product;
import com.surfer.codes.catalogue_service.domain.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    PagedResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") Integer pageNo){
        return productService.getProducts(pageNo);
    }

}

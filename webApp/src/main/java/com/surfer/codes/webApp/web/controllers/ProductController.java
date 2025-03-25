package com.surfer.codes.webApp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping
    String getIndex() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    String getProducts() {
        return "product";
    }
}

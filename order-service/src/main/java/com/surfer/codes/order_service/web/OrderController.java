package com.surfer.codes.order_service.web;

import com.surfer.codes.order_service.domain.OrderService;
import com.surfer.codes.order_service.domain.SecurityService;
import com.surfer.codes.order_service.domain.models.CreateOrderRequest;
import com.surfer.codes.order_service.domain.models.CreateOrderResponse;
import com.surfer.codes.order_service.domain.models.OrderDetailsResponse;
import com.surfer.codes.order_service.domain.models.OrderSummary;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final SecurityService securityService;
    private final OrderService orderService;

    OrderController(SecurityService securityService, OrderService orderService) {
        this.securityService = securityService;
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        String userName = securityService.getLoggedInUserName();
        log.info("Creating order for user: {}", userName);
        return orderService.createOrder(userName, request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<OrderSummary> getOrdersSummary() {
        String user = securityService.getLoggedInUserName();
        return orderService.getOrdersList(user);
    }

    @GetMapping("/{orderNumber}")
    OrderDetailsResponse getOrderDetails(@PathVariable String orderNumber) {
        String userName = securityService.getLoggedInUserName();
        return orderService.getOrderDetails(userName, orderNumber).orElseThrow(NoSuchElementException::new);
    }
}

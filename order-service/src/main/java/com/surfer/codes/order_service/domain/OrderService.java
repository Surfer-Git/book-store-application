package com.surfer.codes.order_service.domain;

import com.surfer.codes.order_service.domain.models.CreateOrderRequest;
import com.surfer.codes.order_service.domain.models.CreateOrderResponse;
import com.surfer.codes.order_service.domain.models.OrderStatus;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;

    public OrderService(OrderRepository orderRepository, OrderValidator orderValidator) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
    }

    public CreateOrderResponse createOrder(String userName, CreateOrderRequest request) {
        // validation from catalogue service side
        orderValidator.validate(request);

        OrderEntity newOrder = OrderMapper.INSTANCE.createOrderRequestToOrderEntity(request);
        newOrder.setUserName(userName);
        newOrder.setOrderNumber(UUID.randomUUID().toString());
        newOrder.setStatus(OrderStatus.NEW);
        OrderEntity savedOrder = this.orderRepository.save(newOrder);
        log.info("Created Order with order-number : {}", savedOrder.getOrderNumber());
        return new CreateOrderResponse(savedOrder.getOrderNumber());
    }
}

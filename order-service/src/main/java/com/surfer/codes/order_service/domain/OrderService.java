package com.surfer.codes.order_service.domain;

import com.surfer.codes.order_service.domain.models.*;
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
    private final OrderMapper orderMapper;
    private final OrderEventService orderEventService;

    public OrderService(
            OrderRepository orderRepository,
            OrderValidator orderValidator,
            OrderEventRepository orderEventRepository,
            OrderMapper orderMapper,
            OrderEventService orderEventService) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.orderMapper = orderMapper;
        this.orderEventService = orderEventService;
    }

    public CreateOrderResponse createOrder(String userName, CreateOrderRequest request) {
        // validation from catalogue service side
        orderValidator.validate(request);

        OrderEntity newOrder = OrderMapper.INSTANCE.createOrderRequestToOrderEntity(request);
        newOrder.setUserName(userName);
        newOrder.setOrderNumber(UUID.randomUUID().toString());
        newOrder.setStatus(OrderStatus.NEW);

        OrderEntity savedOrder = this.orderRepository.save(newOrder);
        CreateOrderEvent createOrderEvent = orderMapper.buildCreateOrderEvent(savedOrder);
        orderEventService.save(createOrderEvent);

        log.info("Created Order with order-number : {}", savedOrder.getOrderNumber());
        return new CreateOrderResponse(savedOrder.getOrderNumber());
    }
}

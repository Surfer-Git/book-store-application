package com.surfer.codes.order_service.domain;

import com.surfer.codes.order_service.domain.models.CreateOrderEvent;
import org.springframework.stereotype.Service;

@Service
public class OrderEventService {

    private final OrderEventRepository orderEventRepository;

    public OrderEventService(OrderEventRepository orderEventRepository) {
        this.orderEventRepository = orderEventRepository;
    }

    void save(CreateOrderEvent createOrderEvent) {
        // todo
    }
}

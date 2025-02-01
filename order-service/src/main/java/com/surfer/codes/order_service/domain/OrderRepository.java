package com.surfer.codes.order_service.domain;

import com.surfer.codes.order_service.domain.models.OrderStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> getOrderByStatus(OrderStatus orderStatus);
}

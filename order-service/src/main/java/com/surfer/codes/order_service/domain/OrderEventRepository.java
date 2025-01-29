package com.surfer.codes.order_service.domain;

import com.surfer.codes.order_service.domain.models.OrderEventType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEventRepository extends JpaRepository<OrderEventEntity, Long> {

    List<OrderEventEntity> findByEventType(OrderEventType eventType);
}

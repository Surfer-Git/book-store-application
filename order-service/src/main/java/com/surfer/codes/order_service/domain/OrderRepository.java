package com.surfer.codes.order_service.domain;

import com.surfer.codes.order_service.domain.models.OrderStatus;
import com.surfer.codes.order_service.domain.models.OrderSummary;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> getOrderByStatus(OrderStatus orderStatus);

    List<OrderSummary> getOrderByUserName(String userName);

    @Query(
            """
            SELECT od FROM
            OrderEntity od LEFT JOIN FETCH od.orderItems
            WHERE od.userName = :userName
            AND od.orderNumber = :orderNumber
            """)
    Optional<OrderEntity> getOrderByUserNameAndOrderNumber(String userName, String orderNumber);
}

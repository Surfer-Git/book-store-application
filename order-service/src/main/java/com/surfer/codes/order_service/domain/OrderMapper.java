package com.surfer.codes.order_service.domain;

import com.surfer.codes.order_service.domain.models.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrderMapper {
    public static OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Autowired
    public SecurityService securityService;

    @Mapping(target = "orderItems", source = "items")
    public abstract OrderEntity createOrderRequestToOrderEntity(CreateOrderRequest request);

    public abstract OrderItemEntity orderItemToOrderItemEntity(OrderItem orderItem);

    public abstract OrderItem orderItemEntityToOrderItem(OrderItemEntity orderItemEntity);

    @Mapping(target = "orderEventType", constant = "ORDER_CREATED")
    @Mapping(target = "eventId", expression = "java(getRandomUUIDstring())")
    public abstract CreateOrderEvent buildCreateOrderEvent(OrderEntity orderEntity);

    @Mapping(target = "orderEventType", constant = "ORDER_DELIVERED")
    @Mapping(target = "eventId", expression = "java(getRandomUUIDstring())")
    public abstract DeliveredOrderEvent buildDeliveredOrderEvent(OrderEntity orderEntity);

    @Mapping(target = "orderEventType", constant = "ORDER_CANCELLED")
    @Mapping(target = "eventId", expression = "java(getRandomUUIDstring())")
    @Mapping(target = "reason", source = "reason")
    public abstract CancelledOrderEvent buildCancelledOrderEvent(OrderEntity orderEntity, String reason);

    @Mapping(target = "orderEventType", constant = "ORDER_PROCESSING_FAILED")
    @Mapping(target = "eventId", expression = "java(getRandomUUIDstring())")
    public abstract ErrorOrderEvent buildErrorOrderEvent(OrderEntity orderEntity);

    @Mapping(target = "totalAmount", expression = "java(calculateOrderCost(orderEntity))")
    public abstract OrderDetailsResponse mapToOrderDetailsResponse(OrderEntity orderEntity);

    public BigDecimal calculateOrderCost(OrderEntity order) {
        return order.getOrderItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }

    public static String getRandomUUIDstring() {
        return UUID.randomUUID().toString();
    }
}

package com.surfer.codes.order_service.domain;

import com.surfer.codes.order_service.domain.models.CreateOrderEvent;
import com.surfer.codes.order_service.domain.models.CreateOrderRequest;
import com.surfer.codes.order_service.domain.models.OrderItem;
import java.time.LocalDateTime;
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
    @Mapping(target = "createdAt", expression = "java(getLocalDateTime())")
    public abstract CreateOrderEvent buildCreateOrderEvent(OrderEntity orderEntity);

    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }
}

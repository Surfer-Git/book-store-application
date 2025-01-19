package com.surfer.codes.order_service.domain;

import com.surfer.codes.order_service.domain.models.CreateOrderRequest;
import com.surfer.codes.order_service.domain.models.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {
    public static OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Autowired
    public SecurityService securityService;

    @Mapping(target = "id", ignore = true)
    //    @Mapping(target = "username", expression = "java(securityService.getLoggedInUserName())")
    @Mapping(target = "orderItems", source = "items")
    //    @Mapping(target = "orderStatus", constant = "java(getNewOrderStatus())")
    //    @Mapping(target = "orderNumber", expression = "java(java.util.UUID.randomUUID().toString())")
    public abstract OrderEntity createOrderRequestToOrderEntity(CreateOrderRequest request);

    @Mapping(target = "id", ignore = true)
    public abstract OrderItemEntity orderItemEntityToOrderItem(OrderItem orderItem);
}

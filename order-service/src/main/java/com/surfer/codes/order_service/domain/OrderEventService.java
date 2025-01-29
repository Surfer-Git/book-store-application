package com.surfer.codes.order_service.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.surfer.codes.order_service.domain.models.CreateOrderEvent;
import com.surfer.codes.order_service.domain.models.OrderEventType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class OrderEventService {

    @Autowired
    private OrderEventRepository orderEventRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderEventPublisher orderEventPublisher;

    void save(CreateOrderEvent createOrderEvent) {
        OrderEventEntity orderEventEntity = new OrderEventEntity();
        orderEventEntity.setOrderNumber(createOrderEvent.orderNumber());
        orderEventEntity.setEventType(createOrderEvent.orderEventType());
        orderEventEntity.setEventId(createOrderEvent.eventId());
        orderEventEntity.setPayload(toJsonPayload(createOrderEvent));

        orderEventRepository.save(orderEventEntity);
    }

    @Scheduled(cron = "10 * * * * *") // every 10-sec
    void publishOrderEvents() {
        Sort sort = Sort.by(Sort.Order.asc("createdAt"));
        List<OrderEventEntity> orderEvents = orderEventRepository.findAll(sort);
        orderEvents.forEach(event -> {
            publish(event);
            orderEventRepository.delete(event);
        });
    }

    void publish(OrderEventEntity orderEventEntity) {
        OrderEventType eventType = orderEventEntity.getEventType();

        switch (eventType) {
            case OrderEventType.ORDER_CREATED:
                CreateOrderEvent orderEvent = readJsonPayload(orderEventEntity.getPayload(), CreateOrderEvent.class);
                orderEventPublisher.publish(orderEvent);
                break;
            default:
                System.out.println("invalid event type");
        }
    }

    private <T> T readJsonPayload(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String toJsonPayload(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.surfer.codes.order_service.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.surfer.codes.order_service.domain.models.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class OrderEventService {

    private final Logger log = LoggerFactory.getLogger(OrderEventService.class);

    @Autowired
    private OrderEventRepository orderEventRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderEventPublisher orderEventPublisher;

    void save(CreateOrderEvent orderEvent) {
        OrderEventEntity orderEventEntity = new OrderEventEntity();
        orderEventEntity.setOrderNumber(orderEvent.orderNumber());
        orderEventEntity.setEventType(orderEvent.orderEventType());
        orderEventEntity.setEventId(orderEvent.eventId());
        orderEventEntity.setPayload(toJsonPayload(orderEvent));

        orderEventRepository.save(orderEventEntity);
    }

    void save(DeliveredOrderEvent orderEvent) {
        OrderEventEntity orderEventEntity = new OrderEventEntity();
        orderEventEntity.setOrderNumber(orderEvent.orderNumber());
        orderEventEntity.setEventType(orderEvent.orderEventType());
        orderEventEntity.setEventId(orderEvent.eventId());
        orderEventEntity.setPayload(toJsonPayload(orderEvent));

        orderEventRepository.save(orderEventEntity);
    }

    void save(CancelledOrderEvent orderEvent) {
        OrderEventEntity orderEventEntity = new OrderEventEntity();
        orderEventEntity.setOrderNumber(orderEvent.orderNumber());
        orderEventEntity.setEventType(orderEvent.orderEventType());
        orderEventEntity.setEventId(orderEvent.eventId());
        orderEventEntity.setPayload(toJsonPayload(orderEvent));

        orderEventRepository.save(orderEventEntity);
    }

    void save(ErrorOrderEvent orderEvent) {
        OrderEventEntity orderEventEntity = new OrderEventEntity();
        orderEventEntity.setOrderNumber(orderEvent.orderNumber());
        orderEventEntity.setEventType(orderEvent.orderEventType());
        orderEventEntity.setEventId(orderEvent.eventId());
        orderEventEntity.setPayload(toJsonPayload(orderEvent));

        orderEventRepository.save(orderEventEntity);
    }

    @Scheduled(cron = "5 * * * * *") // every 5-sec
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
                CreateOrderEvent createOrderEvent =
                        readJsonPayload(orderEventEntity.getPayload(), CreateOrderEvent.class);
                orderEventPublisher.publish(createOrderEvent);
                break;

            case OrderEventType.ORDER_DELIVERED:
                DeliveredOrderEvent deliveredOrderEvent =
                        readJsonPayload(orderEventEntity.getPayload(), DeliveredOrderEvent.class);
                orderEventPublisher.publish(deliveredOrderEvent);
                break;

            case ORDER_CANCELLED:
                CancelledOrderEvent cancelledOrderEvent =
                        readJsonPayload(orderEventEntity.getPayload(), CancelledOrderEvent.class);
                orderEventPublisher.publish(cancelledOrderEvent);
                break;

            case ORDER_PROCESSING_FAILED:
                ErrorOrderEvent errorOrderEvent = readJsonPayload(orderEventEntity.getPayload(), ErrorOrderEvent.class);
                orderEventPublisher.publish(errorOrderEvent);
                break;

            default:
                log.warn(
                        "OrderEventId = {} | Invalid Event-type: {}",
                        orderEventEntity.getEventId(),
                        orderEventEntity.getEventType());
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

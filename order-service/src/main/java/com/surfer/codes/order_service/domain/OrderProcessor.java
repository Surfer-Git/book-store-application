package com.surfer.codes.order_service.domain;

import com.surfer.codes.order_service.domain.models.CancelledOrderEvent;
import com.surfer.codes.order_service.domain.models.DeliveredOrderEvent;
import com.surfer.codes.order_service.domain.models.ErrorOrderEvent;
import com.surfer.codes.order_service.domain.models.OrderStatus;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessor {

    private final Set<String> allowedCountries = Set.of("INDIA", "GERMANY", "JAPAN");
    private final Logger log = LoggerFactory.getLogger(OrderProcessor.class);

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderEventService orderEventService;

    @Scheduled(cron = "${orders-processing.cron}") // every 15-sec
    @SchedulerLock(name = "processNewOrders", lockAtMostFor = "10m")
    void processNewOrders() {
        log.info("Processing of New Orders, STARTED");
        List<OrderEntity> orderList = orderRepository.getOrderByStatus(OrderStatus.NEW);
        orderList.forEach(this::process);
        log.info("Processing of New Orders, COMPLETED");
    }

    @Transactional
    void process(OrderEntity order) {
        try {
            if (isOrderDeliverable(order)) {
                order.setStatus(OrderStatus.DELIVERED);
                order.setUpdatedAt(LocalDateTime.now());
                OrderEntity deliveredOrder = orderRepository.save(order);
                DeliveredOrderEvent orderEvent = OrderMapper.INSTANCE.buildDeliveredOrderEvent(deliveredOrder);
                orderEventService.save(orderEvent);
            } else {
                order.setStatus(OrderStatus.CANCELLED);
                order.setUpdatedAt(LocalDateTime.now());
                OrderEntity cancelledOrder = orderRepository.save(order);
                CancelledOrderEvent orderEvent =
                        OrderMapper.INSTANCE.buildCancelledOrderEvent(cancelledOrder, "NOT deliverable");
                orderEventService.save(orderEvent);
            }
        } catch (Exception e) {
            log.error("OrderId = {} | Order Processing FAILURE: {}", order.getId(), ExceptionUtils.getStackTrace(e));
            order.setStatus(OrderStatus.ERROR);
            order.setUpdatedAt(LocalDateTime.now());
            OrderEntity errorOrder = orderRepository.save(order);
            ErrorOrderEvent orderEvent = OrderMapper.INSTANCE.buildErrorOrderEvent(errorOrder);
            orderEventService.save(orderEvent);
        }
    }

    private boolean isOrderDeliverable(OrderEntity order) {
        return allowedCountries.contains(order.getDeliveryAddress().country().toUpperCase());
    }
}

package com.surfer.codes.order_service.domain.models;

public enum OrderEventType {
    ORDER_CREATED,
    ORDER_DELIVERED,
    ORDER_CANCELLED,
    ORDER_PROCESSING_FAILED;

    @Override
    public String toString() {
        return "event-type: " + name();
    }
}

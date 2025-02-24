package com.surfer.codes.notification_service.events.models;

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

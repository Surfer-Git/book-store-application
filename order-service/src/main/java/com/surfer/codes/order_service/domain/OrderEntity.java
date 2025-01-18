package com.surfer.codes.order_service.domain;

import com.surfer.codes.order_service.domain.models.Address;
import com.surfer.codes.order_service.domain.models.Customer;
import com.surfer.codes.order_service.domain.models.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
class OrderEntity {

    @Id
    @SequenceGenerator(name = "order_id_generator", sequenceName = "order_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_generator")
    private Long id;

    @NotNull(message = "orderNumber can't be null") @Column(nullable = false)
    private String orderNumber;

    @NotNull @Column(name = "username", nullable = false)
    private String userName;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "customer_name")),
        @AttributeOverride(name = "email", column = @Column(name = "customer_email")),
        @AttributeOverride(name = "p[hone", column = @Column(name = "phone"))
    })
    private Customer customer;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "addressLine1", column = @Column(name = "delivery_address_line1")),
        @AttributeOverride(name = "addressLine2", column = @Column(name = "delivery_address_line2")),
        @AttributeOverride(name = "city", column = @Column(name = "delivery_city")),
        @AttributeOverride(name = "state", column = @Column(name = "delivery_state")),
        @AttributeOverride(name = "zipCode", column = @Column(name = "delivery_zip_code")),
        @AttributeOverride(name = "country", column = @Column(name = "delivery_country")),
    })
    private Address deliveryAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String comments;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private Set<OrderItemEntity> orderItems;
}

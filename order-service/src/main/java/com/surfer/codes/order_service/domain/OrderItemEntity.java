package com.surfer.codes.order_service.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItemEntity {

    @Id
    @SequenceGenerator(name = "order_item_id_generator", sequenceName = "order_item_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_id_generator")
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String code;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    @Min(1)
    private Integer quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}

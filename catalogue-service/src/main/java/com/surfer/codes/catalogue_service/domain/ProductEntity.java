package com.surfer.codes.catalogue_service.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_generator")
    @SequenceGenerator(name = "product_id_generator", sequenceName = "product_id_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "product code is required")
    private String code;

    @Column(nullable = false)
    @NotEmpty(message = "product name is required")
    private String name;

    private String description;
    private String imageUrl;

    @NotNull(message = "product price is required") @DecimalMin("0.1")
    @Column(nullable = false)
    private BigDecimal price;
}

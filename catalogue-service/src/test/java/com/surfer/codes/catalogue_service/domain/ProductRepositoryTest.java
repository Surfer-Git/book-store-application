package com.surfer.codes.catalogue_service.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest(
        properties = {
            "spring.test.database.replace=none",
            "spring.datasource.url=jdbc:tc:postgresql:17-alpine:///db",
        })
// @Import(TestcontainersConfiguration.class)
@Sql("/products-test-data.sql")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldGetProductByCode() {
        ProductEntity product = productRepository.findByCode("P101").orElseThrow();
        assertThat(product.getCode()).isEqualTo("P101");
        assertThat(product.getName()).isEqualTo("To Kill a Mockingbird");
    }

    @Test
    void shouldReturnEmptyWhenProductCodeNotExists() {
        assertThat(productRepository.findByCode("random code")).isEmpty();
    }
}

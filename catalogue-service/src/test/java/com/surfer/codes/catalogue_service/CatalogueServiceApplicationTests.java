package com.surfer.codes.catalogue_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class CatalogueServiceApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("Testing GitHub Actions!");
    }

    @Test
    void changeToTestGitHubAction() {}
}

package com.surfer.codes.order_service.client;

import com.surfer.codes.order_service.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class CatalogueServiceClientConfig {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean
    public RestClient catlogueRestClient() {
        return RestClient.builder()
                .baseUrl(applicationProperties.catalogueServiceUrl())
                .build();
    }
}

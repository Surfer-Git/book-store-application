package com.surfer.codes.order_service.client;

import com.surfer.codes.order_service.ApplicationProperties;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class CatalogueServiceClientConfig {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean
    public RestClient catlogueRestClient() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(5));
        factory.setReadTimeout(Duration.ofSeconds(5));

        return RestClient.builder()
                .baseUrl(applicationProperties.catalogueServiceUrl())
                .requestFactory(factory)
                .build();
    }
}

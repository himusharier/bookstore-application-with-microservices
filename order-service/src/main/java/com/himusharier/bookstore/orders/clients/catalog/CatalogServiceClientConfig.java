package com.himusharier.bookstore.orders.clients.catalog;

import com.himusharier.bookstore.orders.ApplicationProperties;
import java.net.http.HttpClient;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
class CatalogServiceClientConfig {

    @Bean
    RestClient restClient(ApplicationProperties properties) {
        /*return RestClient.builder()
        .baseUrl(properties.catalogServiceUrl())
        .build();*/

        // Configure the RestClient with timeouts
        HttpClient httpClient =
                HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();

        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(httpClient);
        requestFactory.setReadTimeout(Duration.ofSeconds(5));

        return RestClient.builder()
                .baseUrl(properties.catalogServiceUrl())
                .requestFactory(requestFactory)
                .build();
    }
}

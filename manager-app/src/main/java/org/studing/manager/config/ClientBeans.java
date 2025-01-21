package org.studing.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.studing.manager.client.ProductsRestClientImpl;

import static org.springframework.web.client.RestClient.builder;

@Configuration
public class ClientBeans {

    @Bean
    public ProductsRestClientImpl productsRestClient(@Value("${shop.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUri) {
        return new ProductsRestClientImpl(builder().baseUrl(catalogueBaseUri).build());
    }
}

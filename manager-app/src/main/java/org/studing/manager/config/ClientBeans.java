package org.studing.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.studing.manager.client.ProductsRestClientImpl;

import static org.springframework.web.client.RestClient.builder;

@Configuration
public class ClientBeans {

    @Bean
    public ProductsRestClientImpl productsRestClient(
        @Value("${shop.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUri,
        @Value("${shop.services.catalogue.username:}") String catalogueUsername,
        @Value("${shop.services.catalogue.password:}") String cataloguePassword) {

        return new ProductsRestClientImpl(
            builder()
                .baseUrl(catalogueBaseUri)
                .requestInterceptor(new BasicAuthenticationInterceptor(catalogueUsername, cataloguePassword))
                .build());
    }
}

package org.studing.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.studing.manager.client.ProductsRestClientImpl;
import org.studing.manager.security.OAuthClientHttpRequestInterceptor;

import static org.springframework.web.client.RestClient.builder;

@Configuration
public class ClientBeans {
    @Bean
    public ProductsRestClientImpl productsRestClient(
        @Value("${shop.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUri,
        ClientRegistrationRepository clientRegistrationRepository,
        OAuth2AuthorizedClientRepository authorizedClientRepository,
        @Value("${shop.services.catalogue.registration-id:keycloak}") String registrationId) {

        return new ProductsRestClientImpl(builder()
            .baseUrl(catalogueBaseUri)
            .requestInterceptor(
                new OAuthClientHttpRequestInterceptor(
                    new DefaultOAuth2AuthorizedClientManager(
                        clientRegistrationRepository,
                        authorizedClientRepository),
                    registrationId))
            .build());
    }
}

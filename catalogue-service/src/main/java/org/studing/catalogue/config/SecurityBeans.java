package org.studing.catalogue.config;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityBeans {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        val scopeEditCatalogue = "SCOPE_edit_catalogue";
        return http
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers(POST, "/catalogue/products/").hasAuthority(scopeEditCatalogue)
                .requestMatchers(PATCH, "catalogue/products/{productId:\\d}").hasAuthority(scopeEditCatalogue)
                .requestMatchers(DELETE, "catalogue/products/{productId:\\d}").hasAuthority(scopeEditCatalogue)
                .requestMatchers(GET).hasAuthority("SCOPE_view_catalogue")
                .anyRequest()
                .denyAll())
            .csrf(CsrfConfigurer::disable)
            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(STATELESS))
            .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(withDefaults()))
            .build();
    }

}

package org.studing.catalogue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityBeans {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(
                authorizeRequests -> authorizeRequests
                    .requestMatchers("catalogue-api/**")
                    .hasRole("SERVICE"))
            .httpBasic(withDefaults())
            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(STATELESS))
            .build();
    }
}

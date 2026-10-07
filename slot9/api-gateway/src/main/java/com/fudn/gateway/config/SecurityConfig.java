package com.fudn.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * TODO 4.10: Bao mat API Gateway bang OAuth2 JWT.
 * - Moi request /api/** phai mang Bearer token hop le tu Keycloak.
 * - Actuator (/actuator/**) mo cho kiem tra suc khoe khong can auth.
 * - CSRF tat + session STATELESS vi day la REST API Bearer-token.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Actuator health/info khong can token
                .requestMatchers("/actuator/**").permitAll()
                // Moi request khac phai xac thuc
                .anyRequest().authenticated()
            )
            // TODO 4.10: bat JWT Resource Server, decoder lay tu issuer-uri (application.properties)
            .oauth2ResourceServer(o -> o.jwt(Customizer.withDefaults()));

        return http.build();
    }
}

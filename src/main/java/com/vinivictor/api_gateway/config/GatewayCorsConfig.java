package com.vinivictor.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class GatewayCorsConfig {

    private final CorsProperties properties;

    public GatewayCorsConfig(CorsProperties properties) {
        this.properties = properties;
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.addAllowedOriginPattern("http://localhost:*");
        cors.addAllowedOriginPattern("http://127.0.0.1:*");
        cors.addAllowedOriginPattern("http://192.168.*.*");
        if (properties.getUrl() != null) {
            properties.getUrl().forEach(cors::addAllowedOriginPattern);
        }
        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        cors.setAllowedHeaders(List.of("*"));
        cors.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }

    @Bean
    public CorsWebFilter corsWebFilter(UrlBasedCorsConfigurationSource corsConfigurationSource) {
        return new CorsWebFilter(corsConfigurationSource);
    }

}

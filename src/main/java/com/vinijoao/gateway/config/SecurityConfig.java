package com.vinijoao.gateway.config;

import com.vinijoao.gateway.security.CookieBearerTokenConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Flux;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final String ADMIN = "ROLE_ADMIN";
    //Aqui estão as regras: login, registro e logout são públicos, e relatório, calendário e escrita em serviços exigem ADMIN.
    // A regra de papel fica em um lugar só.
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http,
            CookieBearerTokenConverter tokenConverter,
            ReactiveJwtAuthenticationConverter jwtAuthenticationConverter) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(Customizer.withDefaults())
                .authorizeExchange(auth -> auth
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .pathMatchers(HttpMethod.POST,
                                "/api/auth/register",
                                "/api/auth/login",
                                "/api/auth/logout").permitAll()
                        .pathMatchers("/api/relatorio/**", "/api/calendario/**").hasAuthority(ADMIN)
                        .pathMatchers(HttpMethod.POST, "/api/servicos/**").hasAuthority(ADMIN)
                        .pathMatchers(HttpMethod.PUT, "/api/servicos/**").hasAuthority(ADMIN)
                        .pathMatchers(HttpMethod.DELETE, "/api/servicos/**").hasAuthority(ADMIN)
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth -> oauth
                        .bearerTokenConverter(tokenConverter)
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)))
                .build();
    }

    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter conversor = new JwtGrantedAuthoritiesConverter();
        conversor.setAuthoritiesClaimName("roles");
        conversor.setAuthorityPrefix("");

        ReactiveJwtAuthenticationConverter reativo = new ReactiveJwtAuthenticationConverter();
        reativo.setJwtGrantedAuthoritiesConverter(jwt -> Flux.fromIterable(conversor.convert(jwt)));
        return reativo;
    }
}

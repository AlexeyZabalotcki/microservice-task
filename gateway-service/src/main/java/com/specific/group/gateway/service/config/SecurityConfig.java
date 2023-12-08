package com.specific.group.gateway.service.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ReactiveAuthenticationManager authenticationManager;
    private final ServerSecurityContextRepository securityContextRepository;

    private static final String ADMIN_ROLE = "ADMIN";
    public static final String USERS = "/users/**";
    public static final String[] USERS_GET = {"/users", "/users/id/{id}", "/users/email/{email}"};
    public static final String[] USERS_PERMIT_ALL = {"/users/authenticate", "/users/sign-in"};
    public static final String[] CLIENT_PERMIT_ALL = {"/client/date/**", "/client/rate/**", "/client/between", "/client/new"};
    public static final String[] CLIENT_PERMIT_ADMIN = {"/client", "/client/id/{id}", "/client/first", "/client/last", "/client/{id}"};

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                        .pathMatchers(HttpMethod.OPTIONS)
                        .permitAll()
                        .pathMatchers(HttpMethod.POST, "/users/authenticate", "/users/sign-in")
                        .permitAll()
                        .pathMatchers(HttpMethod.GET, "/client/date/**", "/client/rate/**", "/client/between")
                        .permitAll()
                        .pathMatchers(HttpMethod.POST, "/client/new")
                        .permitAll()
                        .pathMatchers("/users/**")
                        .hasAuthority(ADMIN_ROLE)
                        .pathMatchers("/client/**")
                        .hasAuthority(ADMIN_ROLE)
                        .anyExchange()
                        .authenticated()
                )
                .securityContextRepository(securityContextRepository)
                .authenticationManager(this.authenticationManager)
                .exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec
                        .authenticationEntryPoint((swe, e) -> {
                            log.error("IN securityWebFilterChain - unauthorized error: {}", e.getMessage());
                            return Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));
                        })
                        .accessDeniedHandler((swe, e) -> {
                            log.error("IN securityWebFilterChain - access denied: {}", e.getMessage());

                            return Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN));
                        }))
                .build();
    }
}

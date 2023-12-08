package com.specific.group.com.user.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Slf4j
@Configuration
class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> root(UserHandler handler) {
        log.info("Configuration routs");
        return RouterFunctions.route()
                .GET("/api/v1/users", handler::findAll)
                .GET("/api/v1/users/id/{id}", handler::findById)
                .GET("/api/v1/users/email/{email}", handler::findByEmail)
                .POST("/api/v1/users", handler::save)
                .POST("/api/v1/users/authenticate", handler::authenticate)
                .POST("/api/v1/users/sign-in", handler::register)
                .PUT("/api/v1/users", handler::update)
                .DELETE("/api/v1/users/{id}", handler::delete)
                .build();
    }
}

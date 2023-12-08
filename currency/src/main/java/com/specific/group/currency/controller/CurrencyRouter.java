package com.specific.group.currency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Slf4j
@Configuration
class CurrencyRouter {

    @Bean
    public RouterFunction<ServerResponse> root(CurrencyHandler handler) {
        log.info("Configuration routs");
        return RouterFunctions.route()
                .GET("/api/v1/currency", handler::findAll)
                .GET("/api/v1/currency/id/{id}", handler::findById)
                .GET("/api/v1/currency/date/{date}", handler::findByDate)
                .GET("/api/v1/currency/rate/{rate}", handler::findByRate)
                .GET("/api/v1/currency/first", handler::findFirst)
                .GET("/api/v1/currency/last", handler::findLast)
                .GET("/api/v1/currency/between", handler::findBetween)
                .POST("/api/v1/currency/new", handler::getAndSave)
                .DELETE("/api/v1/currency/{id}", handler::delete)
                .build();
    }
}

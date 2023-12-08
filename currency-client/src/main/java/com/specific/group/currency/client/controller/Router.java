package com.specific.group.currency.client.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
class Router {

    @Bean
    public RouterFunction<ServerResponse> clientRoot(ClientHandler handler) {
        return RouterFunctions.route()
                .GET("/api/v1/client/date/{date}", handler::findByDate)
                .GET("/api/v1/client/rate/{rate}", handler::findByRate)
                .GET("/api/v1/client/between", handler::findBetween)
                .POST("/api/v1/client/new", handler::getAndSave)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> adminRoot(AdminHandler handler) {
        return RouterFunctions.route()
                .GET("/api/v1/client", handler::findAll)
                .GET("/api/v1/client/id/{id}", handler::findById)
                .GET("/api/v1/client/admin/date/{date}", handler::findByDateAdmin)
                .GET("/api/v1/client/admin/rate/{rate}", handler::findByRateAdmin)
                .GET("/api/v1/client/first", handler::findFirst)
                .GET("/api/v1/client/last", handler::findLast)
                .GET("/api/v1/client/admin/between", handler::findBetweenAdmin)
                .POST("/api/v1/client/new", handler::getAndSave)
                .DELETE("/api/v1/client/{id}", handler::delete)
                .build();
    }
}

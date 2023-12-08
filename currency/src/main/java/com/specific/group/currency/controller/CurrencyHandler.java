package com.specific.group.currency.controller;

import com.mongodb.client.result.DeleteResult;
import com.specific.group.currency.dto.ExchangeRateDto;
import com.specific.group.currency.dto.QueryDto;
import com.specific.group.currency.service.impl.CurrencyServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrencyHandler {

    private final CurrencyServiceImpl service;

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll(), ExchangeRateDto.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        log.info("findById controller");
        String id = serverRequest.pathVariable("id");

        return ServerResponse.ok()
                .body(service.findById(id), ExchangeRateDto.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findByDate(ServerRequest serverRequest) {
        log.info("findByDate controller");
        String date = serverRequest.pathVariable("date");
        Flux<ExchangeRateDto> exchangeRateDtos = service.findByDate(LocalDate.parse(date));
        log.info("findByDate exchangeRateDtoList {}", exchangeRateDtos);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(exchangeRateDtos, ExchangeRateDto.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findByRate(ServerRequest serverRequest) {
        log.info("findByRate controller");
        String rate = serverRequest.pathVariable("rate");
        Flux<ExchangeRateDto> exchangeRateDtos = service.findByRate(rate);

        return ServerResponse.ok()
                .body(exchangeRateDtos, ExchangeRateDto.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findFirst(ServerRequest serverRequest) {
        log.info("findFirst controller");
        Mono<ExchangeRateDto> exchangeRateDto = service.findFirst();

        return ServerResponse.ok()
                .body(exchangeRateDto, ExchangeRateDto.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findLast(ServerRequest serverRequest) {
        log.info("findLast controller");
        Mono<ExchangeRateDto> exchangeRateDto = service.findLast();

        log.info("findLast controller, exchangeRateDto: {}", exchangeRateDto);
        return ServerResponse.ok()
                .body(exchangeRateDto, ExchangeRateDto.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findBetween(ServerRequest serverRequest) {
        log.info("findBetween controller");
        String from = serverRequest.queryParam("from").orElse(null);
        String to = serverRequest.queryParam("to").orElse(null);

        Flux<ExchangeRateDto> exchangeRateDtos = service.findByAmountBetween(from, to);

        return ServerResponse.ok()
                .body(exchangeRateDtos, ExchangeRateDto.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAndSave(ServerRequest serverRequest) {
        log.info("getAndSave controller");
        Mono<QueryDto> query = serverRequest.bodyToMono(QueryDto.class);

        return query.flatMap(queryDto -> {
            Mono<ExchangeRateDto> exchangeRateDto = service.getAndSave(queryDto);
            return ServerResponse.ok()
                    .body(exchangeRateDto, ExchangeRateDto.class)
                    .switchIfEmpty(ServerResponse.notFound().build());
        });
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        log.info("delete controller");
        String id = serverRequest.pathVariable("id");

        return ServerResponse.ok()
                .body(service.delete(id), ExchangeRateDto.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}

package com.specific.group.currency.client.controller;

import com.specific.group.currency.client.dto.QueryDto;
import com.specific.group.currency.client.dto.ResponseDto;
import com.specific.group.currency.client.service.CurrencyClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientHandler {

    private final CurrencyClientService service;

    public Mono<ServerResponse> findByDate(ServerRequest serverRequest) {
        log.info("findByDate controller");
        String date = serverRequest.pathVariable("date");
        Mono<List<ResponseDto>> exchangeRateDtos = service.findByDate(LocalDate.parse(date));
        log.info("findByDate exchangeRateDtoList {}", exchangeRateDtos);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(exchangeRateDtos, ResponseDto.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findByRate(ServerRequest serverRequest) {
        log.info("findByRate controller");
        String rate = serverRequest.pathVariable("rate");

        return ServerResponse.ok()
                .body( service.findByRate(rate), ResponseDto.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findBetween(ServerRequest serverRequest) {
        log.info("findBetween controller");
        String from = serverRequest.queryParam("from").orElse(null);
        String to = serverRequest.queryParam("to").orElse(null);

        return ServerResponse.ok()
                .body(service.findByAmountBetween(from, to), ResponseDto.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAndSave(ServerRequest serverRequest) {
        log.info("getAndSave controller");
        Mono<QueryDto> query = serverRequest.bodyToMono(QueryDto.class);

        String authorization = serverRequest.headers()
                .header("Authorization")
                .stream()
                .findFirst()
                .orElse(null);

        log.info("Token: {}", authorization);

        return query.flatMap(queryDto -> {
            Mono<ResponseDto> exchangeRateDto = service.getAndSave(queryDto, authorization);
            return ServerResponse.ok()
                    .body(exchangeRateDto, ResponseDto.class)
                    .switchIfEmpty(ServerResponse.notFound().build());
        });
    }
}

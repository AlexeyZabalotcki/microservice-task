package com.specific.group.currency.client.controller;

import com.specific.group.currency.client.dto.ExchangeRateDto;
import com.specific.group.currency.client.dto.QueryDto;
import com.specific.group.currency.client.dto.ResponseDto;
import com.specific.group.currency.client.dto.ResponseDtoWithId;
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
public class AdminHandler {

    private final CurrencyClientService service;

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll(), ResponseDtoWithId.class)

                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        log.info("findById controller");
        String id = serverRequest.pathVariable("id");

        return ServerResponse.ok()
                .body(service.findById(id), ResponseDtoWithId.class)

                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> findFirst(ServerRequest serverRequest) {
        log.info("findFirst controller");
        Mono<ResponseDtoWithId> exchangeRateDto = service.findFirst();

        return ServerResponse.ok()
                .body(exchangeRateDto, ResponseDtoWithId.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findLast(ServerRequest serverRequest) {
        log.info("findLast controller");
        Mono<ResponseDtoWithId> exchangeRateDto = service.findLast();

        log.info("findLast controller, exchangeRateDto: {}", exchangeRateDto);
        return ServerResponse.ok()
                .body(exchangeRateDto, ResponseDtoWithId.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        log.info("delete controller");
        String id = serverRequest.pathVariable("id");

        return ServerResponse.ok()
                .body(service.delete(id), Boolean.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findByDateAdmin(ServerRequest serverRequest) {
        log.info("findByDateAdmin controller");
        String date = serverRequest.pathVariable("date");
        Mono<List<ResponseDtoWithId>> exchangeRateDtos = service.findByDateAdmin(LocalDate.parse(date));
        log.info("findByDate exchangeRateDtoList {}", exchangeRateDtos);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(exchangeRateDtos, ResponseDtoWithId.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findByRateAdmin(ServerRequest serverRequest) {
        log.info("findByRateAdmin controller");
        String rate = serverRequest.pathVariable("rate");

        return ServerResponse.ok()
                .body(service.findByRateAdmin(rate), ResponseDtoWithId.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findBetweenAdmin(ServerRequest serverRequest) {
        log.info("findBetweenAdmin controller");
        String from = serverRequest.queryParam("from").orElse(null);
        String to = serverRequest.queryParam("to").orElse(null);

        return ServerResponse.ok()
                .body(service.findByAmountBetweenAdmin(from, to), ResponseDtoWithId.class)
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

        return query.flatMap(queryDto -> {
            Mono<ResponseDto> exchangeRateDto = service.getAndSave(queryDto, authorization);
            return ServerResponse.ok()
                    .body(exchangeRateDto, ResponseDto.class)
                    .switchIfEmpty(ServerResponse.notFound().build());
        });
    }
}

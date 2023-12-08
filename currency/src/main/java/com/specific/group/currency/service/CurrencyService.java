package com.specific.group.currency.service;

import com.mongodb.client.result.DeleteResult;
import com.specific.group.currency.dto.ExchangeRateDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface CurrencyService {

    Mono<ExchangeRateDto> findById(String id);

    Flux<ExchangeRateDto> findAll();

    Mono<ExchangeRateDto> findFirst();

    Mono<ExchangeRateDto> findLast();

    Flux<ExchangeRateDto> findByDate(LocalDate date);

    Flux<ExchangeRateDto> findByRate(String rate);

    Flux<ExchangeRateDto> findByAmountBetween(String from, String to);

    Mono<Boolean> delete(String id);
}


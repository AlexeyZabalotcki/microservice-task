package com.specific.group.currency.client.service.feign_client;

import com.specific.group.currency.client.dto.CurrencyQuery;
import com.specific.group.currency.client.dto.ExchangeRateDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.specific.group.currency.client.util.CurrencyConstants.CURRENCY;

@Service
@ReactiveFeignClient(name = CURRENCY)
public interface CurrencyMsReactiveFeignClient {

    @GetMapping("/api/v1/currency")
    Mono<List<ExchangeRateDto>> findAll();

    @GetMapping("/api/v1/currency/id/{id}")
    Mono<ExchangeRateDto> findById(@PathVariable String id);

    @GetMapping("/api/v1/currency/date/{date}")
    Mono<List<ExchangeRateDto>> findByDate(@PathVariable String date);

    @GetMapping("/api/v1/currency/rate/{rate}")
    Mono<List<ExchangeRateDto>> findByRate(@PathVariable String rate);

    @GetMapping("/api/v1/currency/first")
    Mono<ExchangeRateDto> findFirst();

    @GetMapping("/api/v1/currency/last")
    Mono<ExchangeRateDto> findLast();

    @GetMapping("/api/v1/currency/between")
    Mono<List<ExchangeRateDto>> findBetween(@RequestParam("from") String from,
                                            @RequestParam("to") String to);


    @PostMapping("/api/v1/currency/new")
    Mono<ExchangeRateDto> getAndSave(CurrencyQuery queryDto);

    @DeleteMapping("/api/v1/currency/{id}")
    Mono<Boolean> deleteById(@PathVariable("id") String id);
}

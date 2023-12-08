package com.specific.group.gateway.service.feing_client;

import com.specific.group.gateway.service.dto.currency.ExchangeRateDto;
import com.specific.group.gateway.service.dto.currency.QueryDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.specific.group.gateway.service.constants.CurrencyConstants.CURRENCY_CLIENT;

@Service
@ReactiveFeignClient(name = CURRENCY_CLIENT)
public interface CurrencyMsReactiveFeignClient {

    @GetMapping("/api/v1/client")
    Mono<List<ExchangeRateDto>> findAll();

    @GetMapping("/api/v1/client/id/{id}")
    Mono<ExchangeRateDto> findById(@PathVariable Long id);

    @GetMapping("/api/v1/client/date/{date}")
    Mono<ExchangeRateDto> findByDate(@PathVariable String date);

    @GetMapping("/api/v1/client/rate/{rate}")
    Mono<ExchangeRateDto> findByRate(@PathVariable String rate);

    @GetMapping("/api/v1/client/first")
    Mono<ExchangeRateDto> findFirst();

    @GetMapping("/api/v1/client/last")
    Mono<ExchangeRateDto> findLast();

    @GetMapping("/api/v1/client/between")
    Mono<ExchangeRateDto> findBetween(@RequestParam("from") String from,
                                      @RequestParam("to") String to);


    @PostMapping("/api/v1/client/new")
    Mono<ExchangeRateDto> getAndSave(QueryDto queryDto);

    @DeleteMapping("/api/v1/client/{id}")
    Mono<ExchangeRateDto> deleteById(@PathVariable("id") Long id);
}

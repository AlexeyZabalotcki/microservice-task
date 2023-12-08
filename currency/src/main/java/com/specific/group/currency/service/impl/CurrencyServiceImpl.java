package com.specific.group.currency.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.DeleteResult;
import com.specific.group.currency.dao.CurrencyRateRepository;
import com.specific.group.currency.dto.ExchangeRateDto;
import com.specific.group.currency.dto.QueryDto;
import com.specific.group.currency.mapper.CurrencyMapper;
import com.specific.group.currency.model.ExchangeRate;
import com.specific.group.currency.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final WebClient webClient;
    private final CurrencyMapper mapper;
    private final ObjectMapper objectMapper;
    private final CurrencyRateRepository repository;

    @Value("${currency.base-url}")
    private String baseUrl;

    @Value("${currency.key}")
    private String key;

    @Value("${currency.from}")
    private String from;

    @Value("${currency.to}")
    private String to;

    @Value("${currency.amount}")
    private String amount;


    public Mono<ExchangeRateDto> getAndSave(QueryDto query) {
        BigDecimal amount = query.amount();
        String to = query.to();
        String from = query.from();
        log.info("Create a request: {}, {}, {}", amount, to, from);

        return webClient.get()
                .uri(baseUrl + "to=" + to + "&from=" + from + "&amount=" + amount)
                .header("apikey", key)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(currency -> {
                    try {
                        ExchangeRateDto exchangeRateDto = objectMapper.readValue(currency, ExchangeRateDto.class);
                        ExchangeRate exchangeRate = mapper.toExchangeRate(exchangeRateDto);
                        exchangeRate.setId(UUID.randomUUID().toString());
                        return repository.save(exchangeRate)
                                .then(Mono.just(exchangeRateDto));
                    } catch (JsonProcessingException e) {
                        return Mono.error(e);
                    }
                });
    }


    @Scheduled(fixedRateString = "${currency.fixedRate}")
    public void getAndSaveScheduled() {
        getAndSave(new QueryDto(from, to, new BigDecimal(amount))).subscribe();
        log.info("Created scheduled request: {}, {}, {}", amount, to, from);
    }

    @Override
    public Mono<ExchangeRateDto> findById(String id) {
        log.info("findById service");
        return repository.findById(id)
                .map(mapper::toExchangeRateDto);
    }

    @Override
    public Flux<ExchangeRateDto> findAll() {
        log.info("findAll service");
        return repository.findAll()
                .map(mapper::toExchangeRateDto);
    }

    @Override
    public Mono<ExchangeRateDto> findFirst() {
        log.info("findFirst service");
        return repository.findFirst()
                .map(mapper::toExchangeRateDto);
    }

    @Override
    public Mono<ExchangeRateDto> findLast() {
        log.info("findLast service");
        return repository.findLast()
                .map(mapper::toExchangeRateDto);
    }

    @Override
    public Flux<ExchangeRateDto> findByDate(LocalDate date) {
        log.info("findByDate service");
        log.info("find by date {}", date);
        return repository.findByDate(date)
                .map(mapper::toExchangeRateDto);
    }

    @Override
    public Flux<ExchangeRateDto> findByRate(String rate) {
        log.info("findByRate service");
        return repository.findByRate(new BigDecimal(rate))
                .map(mapper::toExchangeRateDto);
    }

    @Override
    public Flux<ExchangeRateDto> findByAmountBetween(String from, String to) {
        log.info("findByAmountBetween service");
        return repository.findByAmountBetween(new BigDecimal(from), new BigDecimal(to))
                .map(mapper::toExchangeRateDto);
    }

    @Override
    public Mono<Boolean> delete(String id) {
        log.info("delete service");
        return repository.delete(id);
    }
}

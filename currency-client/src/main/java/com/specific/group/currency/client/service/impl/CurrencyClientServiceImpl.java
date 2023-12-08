package com.specific.group.currency.client.service.impl;

import com.specific.group.currency.client.cache.annotation.RedisReactiveCacheAdd;
import com.specific.group.currency.client.cache.annotation.RedisReactiveCacheEvict;
import com.specific.group.currency.client.cache.annotation.RedisReactiveCacheGet;
import com.specific.group.currency.client.dao.QueryRepository;
import com.specific.group.currency.client.dto.*;
import com.specific.group.currency.client.dto.kafka.MessageDto;
import com.specific.group.currency.client.kafka.KafkaService;
import com.specific.group.currency.client.mapper.QueryMapper;
import com.specific.group.currency.client.mapper.ResponseMapper;
import com.specific.group.currency.client.model.Query;
import com.specific.group.currency.client.service.CurrencyClientService;
import com.specific.group.currency.client.service.feign_client.CurrencyMsReactiveFeignClient;
import com.specific.group.currency.client.util.TokenUtil;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyClientServiceImpl implements CurrencyClientService {

    private final CurrencyMsReactiveFeignClient reactiveFeignClient;
    private final KafkaService kafkaService;
    private final ResponseMapper mapper;
    private final QueryMapper queryMapper;
    private final QueryRepository repository;

    @Override
    public Mono<List<ResponseDtoWithId>> findAll() {
        return reactiveFeignClient.findAll()
                .map(dtos -> dtos.stream()
                        .map(mapper::toResponseWithId)
                        .collect(Collectors.toList()));
    }

    @Override
    public Mono<List<ResponseDto>> findByDate(LocalDate date) {
        return reactiveFeignClient.findByDate(date.toString())
                .map(dtos -> dtos.stream()
                        .map(mapper::toResponse)
                        .collect(Collectors.toList()));
    }

    @Override
    public Mono<List<ResponseDtoWithId>> findByDateAdmin(LocalDate date) {
        return reactiveFeignClient.findByDate(date.toString())
                .map(dtos -> dtos.stream()
                        .map(mapper::toResponseWithId)
                        .collect(Collectors.toList()));
    }

    @Override
    @RedisReactiveCacheGet(key = "#id", useArgsHash = true)
    public Mono<ResponseDtoWithId> findById(String id) {
        log.info("findById service");
        return reactiveFeignClient.findById(id)
                .map(mapper::toResponseWithId);
    }

    @Override
    @RedisReactiveCacheAdd(key = "#queryDto.toString()", useArgsHash = true)
    public Mono<ResponseDto> getAndSave(QueryDto queryDto, String authorization) {
        log.info("getAndSave service");
        log.info("queryDto {}", queryDto);
        log.info("authorization {}", authorization);

        String email = TokenUtil.getEmailFromToken(authorization);

        log.info("Email {}", email);

        CurrencyQuery currencyQuery = new CurrencyQuery(queryDto.from(), queryDto.to(), queryDto.amount());
        log.info("currencyQuery {}", currencyQuery);
        Mono<ExchangeRateDto> saved = reactiveFeignClient.getAndSave(currencyQuery);

        Boolean subscribe = queryDto.subscribe();
        log.info("subscribe: {}", subscribe);

        return saved.map(exchangeRateDto -> {
                    ResponseDto response = mapper.toResponse(exchangeRateDto);
                    if (subscribe) {
                        Query query = queryMapper.toQuery(queryDto);
                        query.setEmail(email);
                        Mono<Query> saved1 = repository.save(query);

                        saved1.doOnNext(query1 -> {
                            log.info("SAVED {}", query1);
                        }).subscribe();

                        MessageDto messageDto = MessageDto.builder()
                                .email(email)
                                .payload(response)
                                .build();
                        kafkaService.sendMessage(messageDto);
                    }
                    return response;
                })
                .onErrorResume(throwable -> {
                    if (throwable instanceof WebClientRequestException) {
                        Throwable cause = throwable.getCause();
                        if (cause instanceof ReadTimeoutException) {
                            log.error("Read timeout occurred: {}", cause.getMessage());
                            return Mono.error(throwable);
                        }
                    }

                    log.error("An error occurred: {}", throwable.getMessage());
                    return Mono.error(throwable);
                })
                .onErrorResume(throwable -> Mono.error(new NullPointerException("repeat")));
    }

    public void getAndSaveScheduled(ScheduledQueryDto queryDto) {
        log.info("getAndSaveScheduled service");

        String email = queryDto.email();

        CurrencyQuery currencyQuery = new CurrencyQuery(queryDto.from(), queryDto.to(), queryDto.amount());
        Mono<ExchangeRateDto> saved = reactiveFeignClient.getAndSave(currencyQuery);

        saved.map(exchangeRateDto -> {
            ResponseDto response = mapper.toResponse(exchangeRateDto);
            if (queryDto.subscribe()) {
                MessageDto messageDto = MessageDto.builder()
                        .email(email)
                        .payload(response)
                        .build();
                kafkaService.sendMessage(messageDto);
            }
            return response;
        }).subscribe();
    }

    @Scheduled(cron = "${currency.cron}", zone = "${currency.zone}")
    public void getScheduledRequest() {
        Mono<List<ScheduledQueryDto>> listMono = repository.findAll()
                .flatMap(query -> Mono.just(queryMapper.toScheduledQueryDto(query)))
                .collectList()
                .doOnNext(result -> {
                    System.out.println("Result from repository: " + result);
                });

        listMono.flatMapMany(Flux::fromIterable)
                .doOnNext(this::getAndSaveScheduled)
                .then()
                .subscribe();

        log.info("Scheduled request: ");
    }

    @Override
    public Mono<ResponseDtoWithId> findFirst() {
        log.info("findFirst service");
        return reactiveFeignClient.findFirst()
                .map(mapper::toResponseWithId);
    }

    @Override
    public Mono<ResponseDtoWithId> findLast() {
        log.info("findLast service");
        return reactiveFeignClient.findLast()
                .map(mapper::toResponseWithId);

    }

    @Override
    public Mono<List<ResponseDto>> findByRate(String rate) {
        log.info("findByRate service");
        return reactiveFeignClient.findByRate(rate)
                .map(dtos -> dtos.stream()
                        .map(mapper::toResponse)
                        .collect(Collectors.toList()));
    }

    @Override
    public Mono<List<ResponseDtoWithId>> findByRateAdmin(String rate) {
        log.info("findByRate service");
        return reactiveFeignClient.findByRate(rate)
                .map(dtos -> dtos.stream()
                        .map(mapper::toResponseWithId)
                        .collect(Collectors.toList()));
    }

    @Override
    public Mono<List<ResponseDto>> findByAmountBetween(String from, String to) {
        log.info("findByAmountBetween service");
        return reactiveFeignClient.findBetween(from, to)
                .map(dtos -> dtos.stream()
                        .map(mapper::toResponse)
                        .collect(Collectors.toList()));
    }

    @Override
    public Mono<List<ResponseDtoWithId>> findByAmountBetweenAdmin(String from, String to) {
        log.info("findByAmountBetween service");
        return reactiveFeignClient.findBetween(from, to)
                .map(dtos -> dtos.stream()
                        .map(mapper::toResponseWithId)
                        .collect(Collectors.toList()));
    }

    @Override
    @RedisReactiveCacheEvict(key = "#id", useArgsHash = true)
    public Mono<Boolean> delete(String id) {
        log.info("delete service");
        return reactiveFeignClient.deleteById(id);
    }
}

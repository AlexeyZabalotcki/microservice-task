package com.specific.group.currency.client.service;

import com.specific.group.currency.client.dto.QueryDto;
import com.specific.group.currency.client.dto.ResponseDto;
import com.specific.group.currency.client.dto.ResponseDtoWithId;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyClientService {

     Mono<List<ResponseDtoWithId>>findAll();

     Mono<List<ResponseDto>> findByDate(LocalDate date);

     Mono<List<ResponseDtoWithId>> findByDateAdmin(LocalDate date);

     Mono<ResponseDtoWithId> findById(String id);

     Mono<ResponseDto> getAndSave(QueryDto queryDto, String authorization);

     Mono<ResponseDtoWithId> findFirst();

     Mono<ResponseDtoWithId> findLast();
     Mono<List<ResponseDto>> findByRate(String rate);

     Mono<List<ResponseDtoWithId>> findByRateAdmin(String rate);

     Mono<List<ResponseDto>> findByAmountBetween(String from, String to);
     Mono<List<ResponseDtoWithId>> findByAmountBetweenAdmin(String from, String to);

     Mono<Boolean> delete(String id);

}

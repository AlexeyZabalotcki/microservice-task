package com.specific.group.currency.dao;

import com.mongodb.client.result.DeleteResult;
import com.specific.group.currency.model.ExchangeRate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CurrencyRateRepository {

    private final ReactiveMongoTemplate template;

    public Mono<ExchangeRate> findById(String id) {
        return template.findById(id, ExchangeRate.class);
    }

    public Flux<ExchangeRate> findAll() {
        return template.findAll(ExchangeRate.class);
    }

    public Mono<ExchangeRate> findFirst() {
        Query query = new Query().limit(1);
        return template.findOne(query, ExchangeRate.class).switchIfEmpty(Mono.empty());
    }

    public Mono<ExchangeRate> findLast() {
        return template.findAll(ExchangeRate.class).collectList()
                .flatMap(rateList -> rateList.isEmpty() ? Mono.empty() : Mono.just(rateList.get(rateList.size() - 1)));
    }

    public Flux<ExchangeRate> findByDate(LocalDate date) {
        return template.find(Query.query(Criteria.where("date").is(date)), ExchangeRate.class);
    }

    public Flux<ExchangeRate> findByRate(BigDecimal rate) {
        Query query = Query.query(Criteria.where("info.rate").is(rate));
        return template.find(query, ExchangeRate.class);
    }

    public Flux<ExchangeRate> findByAmountBetween(BigDecimal from, BigDecimal to) {
        Query query = Query.query(Criteria.where("query.amount").gte(from).lte(to));
        return template.find(query, ExchangeRate.class);
    }

    public Mono<ExchangeRate> save(ExchangeRate exchangeRate) {
        return template.save(exchangeRate);
    }

    public Mono<Boolean> delete(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        return template.findAndRemove(query, ExchangeRate.class)
                .hasElement();
    }
}

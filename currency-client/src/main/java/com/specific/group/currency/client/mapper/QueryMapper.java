package com.specific.group.currency.client.mapper;

import com.specific.group.currency.client.dto.QueryDto;
import com.specific.group.currency.client.dto.ScheduledQueryDto;
import com.specific.group.currency.client.model.Query;
import org.springframework.stereotype.Component;

@Component
public class QueryMapper {

    public ScheduledQueryDto toScheduledQueryDto(Query query){
        return new ScheduledQueryDto(query.getEmail(),
                                     query.getFrom(),
                                     query.getTo(),
                                     query.getAmount(),
                                     query.getSubscribe());
    }

    public Query toQuery (QueryDto query){
        return Query.builder()
                .from(query.from())
                .to(query.to())
                .amount(query.amount())
                .subscribe(query.subscribe())
                .build();
    }
}

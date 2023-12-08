package com.specific.group.currency.mapper;

import com.specific.group.currency.dto.ExchangeRateDto;
import com.specific.group.currency.model.ExchangeRate;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    ExchangeRate toExchangeRate(ExchangeRateDto exchangeRateDto);
    ExchangeRateDto toExchangeRateDto(ExchangeRate exchangeRate);
}

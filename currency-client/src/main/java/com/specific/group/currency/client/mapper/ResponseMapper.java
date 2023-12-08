package com.specific.group.currency.client.mapper;

import com.specific.group.currency.client.dto.ExchangeRateDto;
import com.specific.group.currency.client.dto.ResponseDto;
import com.specific.group.currency.client.dto.ResponseDtoWithId;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class ResponseMapper {

    public ResponseDto toResponse(ExchangeRateDto exchangeRateDto) {
        String from = exchangeRateDto.query().from();
        String to = exchangeRateDto.query().to();
        BigDecimal amount = exchangeRateDto.query().amount();
        BigDecimal rate = exchangeRateDto.info().rate();
        LocalDate date = exchangeRateDto.date();
        BigDecimal result = exchangeRateDto.result();
        return new ResponseDto(from, to, amount, rate,date, result);
    }

    public ResponseDtoWithId toResponseWithId(ExchangeRateDto exchangeRateDto) {
        String id = exchangeRateDto.id();
        String from = exchangeRateDto.query().from();
        String to = exchangeRateDto.query().to();
        BigDecimal amount = exchangeRateDto.query().amount();
        BigDecimal rate = exchangeRateDto.info().rate();
        LocalDate date = exchangeRateDto.date();
        BigDecimal result = exchangeRateDto.result();
        return new ResponseDtoWithId(id, from, to, amount, rate, date, result);
    }
}

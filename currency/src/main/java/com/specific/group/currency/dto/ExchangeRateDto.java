package com.specific.group.currency.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExchangeRateDto(
        String id,
        Boolean success,
        QueryDto query,
        InfoDto info,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate date,
        BigDecimal result) {
}

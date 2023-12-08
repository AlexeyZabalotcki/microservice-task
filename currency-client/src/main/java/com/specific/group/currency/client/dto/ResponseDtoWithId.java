package com.specific.group.currency.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ResponseDtoWithId(

        String id,
        String from,
        String to,
        BigDecimal amount,
        BigDecimal rate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate date,
        BigDecimal result
) {
}

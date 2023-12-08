package com.specific.group.currency.dto;

import java.math.BigDecimal;

public record QueryDto(
        String from,
        String to,
        BigDecimal amount) {
}

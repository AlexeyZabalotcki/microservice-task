package com.specific.group.currency.client.dto;

import java.math.BigDecimal;

public record QueryDto(
        String from,
        String to,
        BigDecimal amount,
        Boolean subscribe) {
}

package com.specific.group.gateway.service.dto.currency;

import java.math.BigDecimal;

public record QueryDto(
        String from,
        String to,
        BigDecimal amount,
        Boolean subscribe) {
}

package com.specific.group.currency.client.dto;

import java.math.BigDecimal;

public record ScheduledQueryDto(
        String email,
        String from,
        String to,
        BigDecimal amount,
        Boolean subscribe) {
}

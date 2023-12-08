package com.specific.group.gateway.service.dto.currency;

import java.math.BigDecimal;

public record InfoDto(
        Long timestamp,
        BigDecimal rate) {
}

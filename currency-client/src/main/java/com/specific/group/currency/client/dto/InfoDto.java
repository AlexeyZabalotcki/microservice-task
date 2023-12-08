package com.specific.group.currency.client.dto;

import java.math.BigDecimal;

public record InfoDto(
        Long timestamp,
        BigDecimal rate) {
}

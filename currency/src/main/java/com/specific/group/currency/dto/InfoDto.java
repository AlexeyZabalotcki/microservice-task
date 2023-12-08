package com.specific.group.currency.dto;

import java.math.BigDecimal;

public record InfoDto(
        Long timestamp,
        BigDecimal rate) {
}

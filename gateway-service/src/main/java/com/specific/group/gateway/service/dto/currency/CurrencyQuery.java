package com.specific.group.gateway.service.dto.currency;

import java.math.BigDecimal;

public record CurrencyQuery(String from,
                            String to,
                            BigDecimal amount) {
}

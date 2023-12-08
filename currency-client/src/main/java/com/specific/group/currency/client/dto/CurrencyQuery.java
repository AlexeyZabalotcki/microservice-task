package com.specific.group.currency.client.dto;

import java.math.BigDecimal;

public record CurrencyQuery(String from,
                            String to,
                            BigDecimal amount){
}

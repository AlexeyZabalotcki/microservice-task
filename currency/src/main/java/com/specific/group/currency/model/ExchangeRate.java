package com.specific.group.currency.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@Document(collection = "currency")
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {

    @Id
    private String id;
    private Boolean success;
    private Query query;
    private Info info;
    private LocalDate date;
    private BigDecimal result;
}

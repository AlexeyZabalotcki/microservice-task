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

@Getter
@Setter
@Builder
@ToString
@Document(collection = "query")
@NoArgsConstructor
@AllArgsConstructor
public class Query {

    @Id
    private String id;
    private String from;
    private String to;
    private BigDecimal amount;
}

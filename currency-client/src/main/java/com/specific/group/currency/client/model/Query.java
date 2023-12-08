package com.specific.group.currency.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Table("queries")
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Query {

    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED)
    @Builder.Default
    private UUID id = UUID.randomUUID();
    @CreatedBy
    private String email;
    private String from;
    private String to;
    private BigDecimal amount;
    private Boolean subscribe;
}

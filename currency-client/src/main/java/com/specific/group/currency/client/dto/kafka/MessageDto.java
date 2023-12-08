package com.specific.group.currency.client.dto.kafka;

import com.specific.group.currency.client.dto.ExchangeRateDto;
import com.specific.group.currency.client.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    String email;
    ResponseDto payload;
}

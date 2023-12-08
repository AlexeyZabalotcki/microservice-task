package com.specific.group.mail.sender.utils;

import com.specific.group.mail.sender.dto.BaseMessage;
import com.specific.group.mail.sender.dto.MessagePayload;
import com.specific.group.mail.sender.dto.ResponsePayload;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmailUtils {

    public static StringBuilder getEmailMessage(BaseMessage<? extends MessagePayload> message) {
        ResponsePayload payload = (ResponsePayload) message.getPayload();
        String from = payload.from();
        String to = payload.to();
        BigDecimal amount = payload.amount();
        BigDecimal rate = payload.rate();
        LocalDate date = payload.date();
        BigDecimal result = payload.result();

        return new StringBuilder("The current conversion rate on ")
                .append(date)
                .append(":\n ")
                .append(from)
                .append(" to ")
                .append(to)
                .append(" = ")
                .append(rate)
                .append("\nFor amount: ")
                .append(amount)
                .append("\nResult is: ")
                .append(result);
    }
}

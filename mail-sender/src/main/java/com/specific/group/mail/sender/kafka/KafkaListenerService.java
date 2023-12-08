package com.specific.group.mail.sender.kafka;

import com.specific.group.mail.sender.dto.BaseMessage;
import com.specific.group.mail.sender.dto.ResponsePayload;
import com.specific.group.mail.sender.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaListenerService {

    private final EmailService emailService1;

    @KafkaListener(topics = "${spring.kafka.topics.message}")
    public void listener(BaseMessage<ResponsePayload> message) {
        log.info("listener received: {}", message);
        emailService1.sendMessage(message);
        log.info("message sent: {}", message);
    }
}

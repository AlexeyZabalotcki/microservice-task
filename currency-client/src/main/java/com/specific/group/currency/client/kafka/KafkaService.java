package com.specific.group.currency.client.kafka;

import com.specific.group.currency.client.dto.kafka.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableKafka
@RequiredArgsConstructor
public class KafkaService {

    @Value("${spring.kafka.topics.message}")
    private String topic;

    private final ReactiveKafkaProducerTemplate<String, MessageDto> kafkaTemplate;

    public void sendMessage(MessageDto message) {
        log.info("send to topic={}, {}={},", topic, MessageDto.class.getSimpleName(), message);
        kafkaTemplate.send(topic, message)
                .doOnSuccess(senderResult -> log.info("sent {} offset : {}", message, senderResult.recordMetadata().offset()))
                .subscribe();
    }
}

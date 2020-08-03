package com.chrisgya.kafkaexample.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaSenderWithMessageConverter {
    private final KafkaTemplate<String, ?> kafkaTemplate;

    void sendMessageWithConverter(Message<?> user) {
        log.info("Sending With Message Converter : {}", user);
        log.info("--------------------------------");

        kafkaTemplate.send(user);
    }

}

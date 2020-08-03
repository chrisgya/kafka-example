package com.chrisgya.kafkaexample.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@KafkaListener(id = "class-level", topics = "my-1")
public class KafkaClassListener {

    @KafkaHandler
    void listen(String message) {
        log.info("ClassLevel KafkaHandler[String] {}", message);
    }

    @KafkaHandler(isDefault = true)
    void listenDefault(Object object) {
        log.info("ClassLevel KafkaHandler[Default] {}", object);
    }

}

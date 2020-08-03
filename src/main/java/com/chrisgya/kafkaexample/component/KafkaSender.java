package com.chrisgya.kafkaexample.component;

import com.chrisgya.kafkaexample.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaSender {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RoutingKafkaTemplate routingKafkaTemplate;
    private final KafkaTemplate<String, User> userKafkaTemplate;

    void sendMessage(String message, String topicName) {
        log.info("Sending : {}", message);
        log.info("--------------------------------");

        kafkaTemplate.send(topicName, message);
    }

    void sendWithRoutingTemplate(String message, String topicName) {
        log.info("Sending : {}", message);
        log.info("--------------------------------");

        routingKafkaTemplate.send(topicName, message.getBytes());
    }

    void sendCustomMessage(User user, String topicName) {
        log.info("Sending Json Serializer : {}", user);
        log.info("--------------------------------");

        userKafkaTemplate.send(topicName, user);
    }

    void sendMessageWithCallback(String message, String topicName) {
        log.info("Sending : {}", message);
        log.info("---------------------------------");

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Success Callback: [{}] delivered with offset -{}", message, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.warn("Failure Callback: Unable to deliver message [{}]. {}", message, ex.getMessage());
            }
        });
    }

}

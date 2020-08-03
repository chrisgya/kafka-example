package com.chrisgya.kafkaexample.component;

import com.chrisgya.kafkaexample.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitSend {

    private final KafkaSender kafkaSender;
    private final KafkaSenderWithMessageConverter messageConverterSender;

    @Value("${my.kafka.topic-1}")
    private String topic1;

    @Value("${my.kafka.topic-2}")
    private String topic2;

    @Value("${my.kafka.topic-3}")
    private String topic3;

    @EventListener
    void initiateSendingMessage(ApplicationReadyEvent event) throws InterruptedException {
        Thread.sleep(5000);
        log.info("---------------------------------");
        kafkaSender.sendMessage("I'll be received by MultipleTopicListener, Listener & ClassLevel KafkaHandler", topic1);

        Thread.sleep(5000);
        log.info("---------------------------------");
        kafkaSender.sendMessage("I'll be received by ListenToPartitionWithOffset", topic3);

        Thread.sleep(5000);
        log.info("---------------------------------");
        kafkaSender.sendMessageWithCallback("I'll get a async Callback", "my-others");

        Thread.sleep(5000);
        log.info("---------------------------------");
        kafkaSender.sendMessageWithCallback("I'm sent using RoutingTemplate", "my-bytes");

        Thread.sleep(5000);
        log.info("---------------------------------");
        kafkaSender.sendMessage("I'll be ignored by RecordFilter", topic3);

        Thread.sleep(5000);
        log.info("---------------------------------");
        kafkaSender.sendMessage("I will get reply back from @SendTo", "my-others");


        Thread.sleep(5000);
        log.info("---------------------------------");
        kafkaSender.sendCustomMessage(new User("Christian"), "my-user");

        Thread.sleep(5000);
        log.info("---------------------------------");
        messageConverterSender.sendMessageWithConverter(new GenericMessage<>(new User("Mary")));
    }

}

package com.chrisgya.kafkaexample.component;

import com.chrisgya.kafkaexample.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListeners {
    @KafkaListener(topics = "my-1")
    void listener(String message) {
        log.info("Listener [{}]", message);
    }

    @KafkaListener(topics = { "my-1", "my-2" }, groupId = "my-group-2")
    void commonListenerForMultipleTopics(String message) {
        log.info("MultipleTopicListener - [{}]", message);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "my-3", partitionOffsets = {
            @PartitionOffset(partition = "0", initialOffset = "0") }), groupId = "my-group-3")
    void listenToPartitionWithOffset(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                                     @Header(KafkaHeaders.OFFSET) int offset) {
        log.info("ListenToPartitionWithOffset [{}] from partition-{} with offset-{}", message, partition, offset);
    }

    @KafkaListener(topics = "my-bytes")
    void listenerForRoutingTemplate(String message) {
        log.info("RoutingTemplate BytesListener [{}]", message);
    }

    @KafkaListener(topics = "my-others")
    @SendTo("my-2")
    String listenAndReply(String message) {
        log.info("ListenAndReply [{}]", message);
        return "This is a reply sent to 'my-2' topic after receiving message at 'my-others' topic";
    }

    @KafkaListener(id = "1", topics = "my-user", groupId = "my-user-mc", containerFactory = "userKafkaListenerContainerFactory")
    void listenerWithMessageConverter(User user) {
        log.info("MessageConverterUserListener [{}]", user);
    }
}

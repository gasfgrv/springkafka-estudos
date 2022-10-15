package br.com.gusta.springkafka.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TesteListener {

    @KafkaListener(topics = { "topic-1" }, groupId = "group-1")
    public void listen(String message,
                       ConsumerRecordMetadata metadata,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic
    ) {
        log.info("Topic: {} - Partition: {} - Message: {}",
                topic,
                metadata.partition(),
                message);
    }

}

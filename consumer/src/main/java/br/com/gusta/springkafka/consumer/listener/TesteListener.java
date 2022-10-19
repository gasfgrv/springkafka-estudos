package br.com.gusta.springkafka.consumer.listener;

import br.com.gusta.springkafka.consumer.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TesteListener {

    @KafkaListener(topics = { "topic-1" }, groupId = "group-1", concurrency = "2")
    public void listen(String message,
            ConsumerRecordMetadata metadata,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Thread: {} - Topic: {} - Partition: {} - Message: {}",
                Thread.currentThread().getId(),
                topic,
                metadata.partition(),
                message);
    }

    @KafkaListener(topicPartitions = { @TopicPartition(topic = "my-topic", partitions = "0") }, groupId = "my-group")
    public void listen2(String message, ConsumerRecordMetadata metadata) throws InterruptedException {
        log.info("Message: {}", message);
    }

    @KafkaListener(topicPartitions = { @TopicPartition(topic = "my-topic", partitions = "1-9") }, groupId = "my-group")
    public void listen3(String message, ConsumerRecordMetadata metadata) {
        log.info("Partition: {} - Message: {}", metadata.partition(), message);
    }

    @CustomListener(groupId = "group-person")
    public void listenPerson(Person person) {
        log.info(person.toString());
    }

}

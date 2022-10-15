package br.com.gusta.springkafka.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TesteListener {

    @KafkaListener(topics = { "topic-1" }, groupId = "group-1")
    public void listen(String message) {
        log.info("Thread: {}", Thread.currentThread().getId());
        log.info("Message: {}", message);
    }

}

package br.com.gusta.springkafka.producer.controller;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public TesteController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/send")
    public ResponseEntity<Void> send() {
        IntStream.range(1, 50).boxed()
                .forEach(i -> kafkaTemplate.send("topic-1", String.valueOf(OffsetDateTime.now())));
        return ResponseEntity.noContent().build();
    }

}

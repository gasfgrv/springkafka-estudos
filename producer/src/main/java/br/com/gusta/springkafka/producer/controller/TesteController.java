package br.com.gusta.springkafka.producer.controller;

import br.com.gusta.springkafka.producer.model.Person;
import java.io.Serializable;
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
    private final KafkaTemplate<String, Serializable> serializableKafkaTemplate;

    @Autowired
    public TesteController(KafkaTemplate<String, String> kafkaTemplate,
                           KafkaTemplate<String, Serializable> serializableKafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.serializableKafkaTemplate = serializableKafkaTemplate;
    }

    @GetMapping("/send")
    public ResponseEntity<Void> send() {
        IntStream.range(1, 10).boxed()
                .forEach(i -> kafkaTemplate.send("topic-1", String.valueOf(OffsetDateTime.now())));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/send/person")
    public ResponseEntity<Void> sendPerson() {
        serializableKafkaTemplate.send("person-topic", new Person("Gustavo", 25));
        return ResponseEntity.ok().build();
    }

}

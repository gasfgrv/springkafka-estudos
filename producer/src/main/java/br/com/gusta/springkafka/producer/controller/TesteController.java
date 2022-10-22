package br.com.gusta.springkafka.producer.controller;

import java.io.Serializable;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gusta.springkafka.producer.model.City;
import br.com.gusta.springkafka.producer.model.Person;

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
        kafkaTemplate.send("topic-1", String.valueOf(OffsetDateTime.now()));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/send/person")
    public ResponseEntity<Void> sendPerson() {
        serializableKafkaTemplate.send("person-topic", new Person("Gustavo", 25));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/send/city")
    public ResponseEntity<Void> sendCity() {
        serializableKafkaTemplate.send("city-topic", new City("São Paulo", "SP"));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/send2")
    public ResponseEntity<Void> sendMyTopic() {
        kafkaTemplate.send("my-topic", "Lorem Ipsum");
        return ResponseEntity.noContent().build();
    }
}

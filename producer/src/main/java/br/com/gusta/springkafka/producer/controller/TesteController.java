package br.com.gusta.springkafka.producer.controller;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gusta.springkafka.producer.model.City;
import br.com.gusta.springkafka.producer.model.Person;

@RestController
public class TesteController {

    private final RoutingKafkaTemplate routingKafkaTemplate;

    @Autowired
    public TesteController(RoutingKafkaTemplate routingKafkaTemplate) {
        this.routingKafkaTemplate = routingKafkaTemplate;
    }

    @GetMapping("/send")
    public ResponseEntity<Void> send() {
        routingKafkaTemplate.send("topic-1", String.valueOf(OffsetDateTime.now()));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/send/person")
    public ResponseEntity<Void> sendPerson() {
        routingKafkaTemplate.send("person-topic", new Person("Gustavo", 25));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/send/city")
    public ResponseEntity<Void> sendCity() {
        routingKafkaTemplate.send("city-topic", new City("São Paulo", "SP"));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/send2")
    public ResponseEntity<Void> sendMyTopic() {
        routingKafkaTemplate.send("my-topic", "Lorem Ipsum");
        return ResponseEntity.noContent().build();
    }
}

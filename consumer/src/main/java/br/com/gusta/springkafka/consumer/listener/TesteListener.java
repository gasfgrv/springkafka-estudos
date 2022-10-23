package br.com.gusta.springkafka.consumer.listener;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import br.com.gusta.springkafka.consumer.model.City;
import br.com.gusta.springkafka.consumer.model.Person;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TesteListener {

    @KafkaListener(topics = {"topic-1"}, groupId = "group-1")
    public void listen(String message[], @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Thread: {} - Topic: {} - Message: {}",
                Thread.currentThread().getId(),
                topic,
                message);
    }

    @KafkaListener(topics = "my-topic", groupId = "my-group")
    public void listen2(String[] message) {
        log.info("Thread: {} - Message: {}",
                Thread.currentThread().getId(),
                message);
    }

    @CustomListener(groupId = "group-person")
    public void listenPerson(Person person) {
        log.info(person.toString());
    }

    @KafkaListener(topics = "city-topic",
            groupId = "group-city",
            containerFactory = "dynamicJsonKafkaListenerContainerFactory")
    public void listenCity(List<City> city) {
        log.info(city.toString());
    }

}

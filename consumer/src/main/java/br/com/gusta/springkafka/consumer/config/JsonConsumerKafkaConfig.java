package br.com.gusta.springkafka.consumer.config;

import java.util.HashMap;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.RecordInterceptor;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import br.com.gusta.springkafka.consumer.model.Person;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class JsonConsumerKafkaConfig {

    private final KafkaProperties kafkaProperties;

    @Autowired
    public JsonConsumerKafkaConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public ConsumerFactory<String, Person> personConsumerFactory() {
        try (var valueDeserializer = new JsonDeserializer<>(Person.class)
                .trustedPackages("br.com.gusta.springkafka.producer.model").forKeys()) {
            var configs = new HashMap<String, Object>();
            configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
            configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
            var keyDeserializer = new StringDeserializer();
            return new DefaultKafkaConsumerFactory<>(configs, keyDeserializer, valueDeserializer);
        }
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Person> personKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, Person>();
        factory.setConsumerFactory(personConsumerFactory());
        factory.setRecordInterceptor(exampleInterceptor());
        return factory;
    }

    private RecordInterceptor<String, Person> exampleInterceptor() {
        return new RecordInterceptor<>() {
            @Override
            public ConsumerRecord<String, Person> intercept(ConsumerRecord<String, Person> record) {
                return record;
            }

            @Override
            public void success(ConsumerRecord<String, Person> record, Consumer<String, Person> consumer) {
                log.info("Sucesso");
            }

            @Override
            public void failure(ConsumerRecord<String, Person> record, Exception exception, Consumer<String, Person> consumer) {
                log.info("Falha");
            }
        };
    }

    private RecordInterceptor<String, Person> adultInterceptor() {
        return recordData -> {
            var person = recordData.value();
            return person.getAge() >= 18
                    ? recordData
                    : null;
        };
    }

}

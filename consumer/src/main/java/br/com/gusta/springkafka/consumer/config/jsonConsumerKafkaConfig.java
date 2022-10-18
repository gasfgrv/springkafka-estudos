package br.com.gusta.springkafka.consumer.config;

import br.com.gusta.springkafka.consumer.model.Person;
import java.util.HashMap;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class jsonConsumerKafkaConfig {

    private final KafkaProperties kafkaProperties;

    @Autowired
    public jsonConsumerKafkaConfig(KafkaProperties kafkaProperties) {
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
        return factory;
    }

}

package br.com.gusta.springkafka.producer.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class RoutingKafkaTemplateConfig {

    private final KafkaProperties kafkaProperties;

    @Autowired
    public RoutingKafkaTemplateConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }
    private ProducerFactory jsonProducerFactory() {
        var configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory(configs, new StringSerializer(), new JsonSerializer<>());
    }
    
    @Bean
    public RoutingKafkaTemplate routingTemplate(GenericApplicationContext context, ProducerFactory producerFactory) {
        var jsonProducerFactory = jsonProducerFactory();
        context.registerBean(DefaultKafkaProducerFactory.class, "jsonPF", jsonProducerFactory);
        var map = new LinkedHashMap<Pattern, ProducerFactory<Object, Object>>();
        map.put(Pattern.compile("topic-.*"), producerFactory);
        map.put(Pattern.compile(".*-topic"), jsonProducerFactory);
        return new RoutingKafkaTemplate(map);
    }
}

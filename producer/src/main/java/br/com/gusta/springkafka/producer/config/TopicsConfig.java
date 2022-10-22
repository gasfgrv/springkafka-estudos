package br.com.gusta.springkafka.producer.config;

import java.util.HashMap;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

public class TopicsConfig {
    // Configuração de tópico via código -> O correto é que os tópicos sejam criados
    // pela equipe de infra
    @Bean
    public KafkaAdmin admin() {
        var configs = new HashMap<String, Object>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new KafkaAdmin(configs);
    }

    public KafkaAdmin.NewTopics topics() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("topic-1")
                        .partitions(2)
                        .replicas(1)
                        .build(),
                TopicBuilder.name("person-topic")
                        .partitions(2)
                        .replicas(1)
                        .build(),
                TopicBuilder.name("my-topic")
                        .partitions(10)
                        .replicas(1)
                        .build(),
                TopicBuilder.name("city-topic")
                        .partitions(2)
                        .replicas(1)
                        .build());
    }
}

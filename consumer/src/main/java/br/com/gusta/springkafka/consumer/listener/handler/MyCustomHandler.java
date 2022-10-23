package br.com.gusta.springkafka.consumer.listener.handler;

import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyCustomHandler implements KafkaListenerErrorHandler {@Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
        log.info("Payload: {}", message.getPayload());
        log.info("Exception: {}", exception.toString());
        return null;
    }
    
}

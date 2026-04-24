package com.company.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("tracking.events.exchange")
    private String exchangeName;

    @Value("tracking.events.queue")
    private String queueName;

    @Value("tracking.events.routing-key")
    private String routingKey;

    @Bean
    public DirectExchange trackingExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Queue trackingQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public Binding trackingBinding(DirectExchange trackingExchange, Queue trackingQueue) {
        return BindingBuilder.bind(trackingQueue).to(trackingExchange).with(routingKey);
    }
}

package com.company.producer;

import com.company.model.ParcelTrackingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProducerClass {

    private static final Logger log = LoggerFactory.getLogger(ProducerClass.class);

    private final KafkaTemplate<String, ParcelTrackingEvent> kafkaTemplate;
    private final String topic;

    public ProducerClass(KafkaTemplate<String, ParcelTrackingEvent> kafkaTemplate,
                         @Value("${tracking.kafka.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void publishTrackingEvent(ParcelTrackingEvent event) {
        log.info("Publishing tracking event for parcelId={}", event.getParcelId());
        kafkaTemplate.send(topic, event.getParcelId(), event);
    }
}

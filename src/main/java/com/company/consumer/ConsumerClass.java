package com.company.consumer;

import com.company.model.ParcelTrackingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerClass {

    private static final Logger log = LoggerFactory.getLogger(ConsumerClass.class);

    @KafkaListener(topics = "${tracking.kafka.topic}", containerFactory = "trackingEventKafkaListenerContainerFactory")
    public void handleTrackingEvent(ParcelTrackingEvent event) {
        log.info("Consumed tracking event for parcelId={} with status={}", event.getParcelId(), event.getStatus());
    }
}

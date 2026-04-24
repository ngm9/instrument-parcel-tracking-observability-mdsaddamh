package com.company.service;

import com.company.dto.TrackUpdateRequest;
import com.company.dto.TrackUpdateResponse;
import com.company.exception.BusinessException;
import com.company.model.ParcelTrackingEntity;
import com.company.model.ParcelTrackingEvent;
import com.company.repository.RepositoryClass;
import com.company.producer.ProducerClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class ServiceClass {

    private static final Logger log = LoggerFactory.getLogger(ServiceClass.class);

    private final RepositoryClass repository;
    private final ProducerClass producer;
    private final CarrierClient carrierClient;

    public ServiceClass(RepositoryClass repository, ProducerClass producer, CarrierClient carrierClient) {
        this.repository = repository;
        this.producer = producer;
        this.carrierClient = carrierClient;
    }

    @Transactional
    public TrackUpdateResponse updateTracking(TrackUpdateRequest request) {
        ParcelTrackingEntity entity = repository.findByParcelId(request.getParcelId())
                .orElseGet(() -> new ParcelTrackingEntity(request.getParcelId()));

        entity.setStatus(request.getStatus());
        entity.setCarrierCode(request.getCarrierCode());
        entity.setLastUpdated(Instant.now());

        ParcelTrackingEntity saved = repository.save(entity);
        log.info("Persisted tracking update for parcelId={}", saved.getParcelId());

        ParcelTrackingEvent event = new ParcelTrackingEvent(
                saved.getParcelId(),
                saved.getStatus(),
                saved.getCarrierCode(),
                saved.getLastUpdated()
        );
        producer.publishTrackingEvent(event);

        String carrierStatus = carrierClient.fetchCarrierStatus(saved.getCarrierCode(), saved.getParcelId())
                .orElse(saved.getStatus());

        TrackUpdateResponse response = new TrackUpdateResponse();
        response.setParcelId(saved.getParcelId());
        response.setStatus(saved.getStatus());
        response.setCarrierStatus(carrierStatus);
        response.setLastUpdated(saved.getLastUpdated());
        return response;
    }

    @Transactional(readOnly = true)
    public TrackUpdateResponse getTracking(String parcelId) {
        ParcelTrackingEntity entity = repository.findByParcelId(parcelId)
                .orElseThrow(() -> new BusinessException("Parcel not found: " + parcelId));

        TrackUpdateResponse response = new TrackUpdateResponse();
        response.setParcelId(entity.getParcelId());
        response.setStatus(entity.getStatus());
        response.setCarrierStatus(entity.getStatus());
        response.setLastUpdated(entity.getLastUpdated());
        return response;
    }
}

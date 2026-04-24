package com.company.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class HttpCarrierClient implements CarrierClient {

    private static final Logger log = LoggerFactory.getLogger(HttpCarrierClient.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Optional<String> fetchCarrierStatus(String carrierCode, String parcelId) {
        String url = "http://localhost:8081/carrier/track/" + parcelId;
        try {
            String status = restTemplate.getForObject(url, String.class);
            return Optional.ofNullable(status);
        } catch (RestClientException ex) {
            log.warn("Failed to fetch carrier status from {} for parcelId={}", carrierCode, parcelId);
            return Optional.empty();
        }
    }
}

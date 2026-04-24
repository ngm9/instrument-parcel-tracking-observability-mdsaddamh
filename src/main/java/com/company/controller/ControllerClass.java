package com.company.controller;

import com.company.dto.TrackUpdateRequest;
import com.company.dto.TrackUpdateResponse;
import com.company.service.ServiceClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/track")
public class ControllerClass {

    private static final Logger log = LoggerFactory.getLogger(ControllerClass.class);

    private final ServiceClass serviceClass;

    public ControllerClass(ServiceClass serviceClass) {
        this.serviceClass = serviceClass;
    }

    @PostMapping("/update")
    public ResponseEntity<TrackUpdateResponse> updateTracking(@RequestBody TrackUpdateRequest request) {
        log.info("Received tracking update for parcelId={}", request.getParcelId());
        TrackUpdateResponse response = serviceClass.updateTracking(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{parcelId}")
    public ResponseEntity<TrackUpdateResponse> getTracking(@PathVariable String parcelId) {
        log.info("Received tracking query for parcelId={}", parcelId);
        TrackUpdateResponse response = serviceClass.getTracking(parcelId);
        return ResponseEntity.ok(response);
    }
}

package com.company.model;

import java.time.Instant;

public class ParcelTrackingEvent {

    private String parcelId;
    private String status;
    private String carrierCode;
    private Instant lastUpdated;

    public ParcelTrackingEvent() {
    }

    public ParcelTrackingEvent(String parcelId, String status, String carrierCode, Instant lastUpdated) {
        this.parcelId = parcelId;
        this.status = status;
        this.carrierCode = carrierCode;
        this.lastUpdated = lastUpdated;
    }

    public String getParcelId() {
        return parcelId;
    }

    public void setParcelId(String parcelId) {
        this.parcelId = parcelId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}

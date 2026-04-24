package com.company.dto;

import java.time.Instant;

public class TrackUpdateRequest {

    private String parcelId;
    private String status;
    private String carrierCode;

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
}

class TrackUpdateResponse {

    private String parcelId;
    private String status;
    private String carrierStatus;
    private Instant lastUpdated;

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

    public String getCarrierStatus() {
        return carrierStatus;
    }

    public void setCarrierStatus(String carrierStatus) {
        this.carrierStatus = carrierStatus;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}

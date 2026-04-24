package com.company.service;

import java.util.Optional;

public interface CarrierClient {

    Optional<String> fetchCarrierStatus(String carrierCode, String parcelId);
}

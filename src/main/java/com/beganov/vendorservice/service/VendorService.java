package com.beganov.vendorservice.service;

import com.beganov.vendorservice.dto.VendorResponse;

import java.util.List;

public interface VendorService {

    List<VendorResponse> getVendorsFromExternalApi();
    String pushDataToExternalVendor(Long id);
}

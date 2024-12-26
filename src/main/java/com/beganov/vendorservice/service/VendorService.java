package com.beganov.vendorservice.service;

import com.beganov.vendorservice.dto.VendorRequest;
import com.beganov.vendorservice.dto.VendorResponse;

import java.util.List;

public interface VendorService {

    List<VendorResponse> getAllVendors();
    VendorResponse getVendorById();
    void pushDataToVendor(VendorRequest request);
}

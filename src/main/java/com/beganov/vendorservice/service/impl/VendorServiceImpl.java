package com.beganov.vendorservice.service.impl;

import com.beganov.vendorservice.dto.VendorRequest;
import com.beganov.vendorservice.dto.VendorResponse;
import com.beganov.vendorservice.service.VendorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    @Override
    public List<VendorResponse> getAllVendors() {
        return List.of();
    }

    @Override
    public VendorResponse getVendorById() {
        return null;
    }

    @Override
    public void pushDataToVendor(VendorRequest request) {

    }
}

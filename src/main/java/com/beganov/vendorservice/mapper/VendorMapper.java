package com.beganov.vendorservice.mapper;

import com.beganov.vendorservice.dto.VendorRequest;
import com.beganov.vendorservice.dto.VendorResponse;
import com.beganov.vendorservice.model.Vendor;
import org.springframework.stereotype.Component;

@Component
public class VendorMapper {

    public VendorResponse toResponse(Vendor vendor) {
        VendorResponse vendorResponse = new VendorResponse();
        vendorResponse.setId(vendor.getId());
        vendorResponse.setName(vendor.getName());
        vendorResponse.setAddress(vendor.getAddress());
        vendorResponse.setProduct(vendor.getProduct());
        vendorResponse.setDescription(vendor.getDescription());
        return vendorResponse;
    }

    public Vendor toEntity(VendorRequest request) {
        Vendor vendor = new Vendor();
        vendor.setName(request.getName());
        vendor.setAddress(request.getAddress());
        vendor.setProduct(request.getProduct());
        vendor.setDescription(request.getDescription());
        return vendor;
    }
}

package com.beganov.vendorservice.controller;

import com.beganov.vendorservice.dto.VendorResponse;
import com.beganov.vendorservice.service.VendorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    public List<VendorResponse> getAllVendors() {
        return vendorService.getVendorsFromExternalApi();
    }

    @PostMapping("/{id}")
    public String pushDataToVendor(@Valid @PathVariable Long id) {
        return vendorService.pushDataToExternalVendor(id);
    }
}

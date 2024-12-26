package com.beganov.vendorservice.controller;

import com.beganov.vendorservice.dto.VendorResponse;
import com.beganov.vendorservice.service.VendorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VendorController.class)
class VendorControllerTest {

    @MockBean
    private VendorService vendorService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllVendors() throws Exception {
        List<VendorResponse> mockVendors = List.of(
                new VendorResponse(1L, "Vendor 1", "Address 1", "Product 1", "Description 1"),
                new VendorResponse(2L, "Vendor 2", "Address 2", "Product 2", "Description 2")
        );

        when(vendorService.getVendorsFromExternalApi()).thenReturn(mockVendors);

        mockMvc.perform(get("/api/vendors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }
}
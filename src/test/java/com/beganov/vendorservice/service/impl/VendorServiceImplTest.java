package com.beganov.vendorservice.service.impl;

import com.beganov.vendorservice.dto.VendorRequest;
import com.beganov.vendorservice.dto.VendorResponse;
import com.beganov.vendorservice.exception.VendorNotFoundException;
import com.beganov.vendorservice.mapper.VendorMapper;
import com.beganov.vendorservice.model.Vendor;
import com.beganov.vendorservice.repository.VendorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class VendorServiceImplTest {

    @Mock
    private VendorRepository vendorRepository;

    @Mock
    private VendorMapper vendorMapper;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private VendorServiceImpl vendorService;

    @Test
    void getVendorsFromExternalApi_ShouldSaveAndReturnVendors() {
        List<VendorRequest> fakeVendors = List.of(
                new VendorRequest("Vendor 1", "Address 1", "Product 1", "Description 1"),
                new VendorRequest("Vendor 2", "Address 2", "Product 2", "Description 2")
        );
        List<Vendor> vendors = fakeVendors.stream()
                .map(req -> new Vendor(null, req.getName(), req.getAddress(), req.getProduct(), req.getDescription()))
                .toList();

        // Mock поведения
        Mockito.when(vendorMapper.toEntity(any(VendorRequest.class)))
                .thenAnswer(invocation -> {
                    VendorRequest req = invocation.getArgument(0);
                    return new Vendor(null, req.getName(), req.getAddress(), req.getProduct(), req.getDescription());
                });

        Mockito.when(vendorMapper.toResponse(any(Vendor.class)))
                .thenAnswer(invocation -> {
                    Vendor v = invocation.getArgument(0);
                    return new VendorResponse(v.getId(), v.getName(), v.getAddress(), v.getProduct(), v.getDescription());
                });

        Mockito.when(vendorRepository.saveAll(anyList())).thenReturn(vendors);

        // Act
        List<VendorResponse> result = vendorService.getVendorsFromExternalApi();

        // Assert
        assertNotNull(result);
        assertEquals(fakeVendors.size(), result.size());
        verify(vendorRepository).saveAll(anyList());
    }

    @Test
    void pushDataToExternalVendor_ShouldSendVendorDataToApi() {
        Long vendorId = 1L;
        Vendor vendor = new Vendor(vendorId, "Vendor 1", "Address 1", "Product 1", "Description 1");
        VendorResponse vendorResponse = new VendorResponse(vendor.getId(), vendor.getName(), vendor.getAddress(), vendor.getProduct(), vendor.getDescription());
        String externalApiUrl = "https://third-party-api.com/vendors";

        // Mock поведения
        Mockito.when(vendorRepository.findById(vendorId)).thenReturn(Optional.of(vendor));
        Mockito.when(vendorMapper.toResponse(vendor)).thenReturn(vendorResponse);

        // Act
        String result = vendorService.pushDataToExternalVendor(vendorId);

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("was sent to"));
        verify(restTemplate).postForObject(eq(externalApiUrl), eq(vendorResponse), eq(String.class));
    }

    @Test
    void pushDataToExternalVendor_ShouldThrowException_WhenVendorNotFound() {
        // Arrange
        Long vendorId = 1L;
        Mockito.when(vendorRepository.findById(vendorId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(VendorNotFoundException.class, () -> vendorService.pushDataToExternalVendor(vendorId));
        verifyNoInteractions(restTemplate);
    }
}
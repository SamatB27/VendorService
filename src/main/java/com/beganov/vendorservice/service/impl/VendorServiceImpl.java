package com.beganov.vendorservice.service.impl;

import com.beganov.vendorservice.dto.VendorRequest;
import com.beganov.vendorservice.dto.VendorResponse;
import com.beganov.vendorservice.exception.VendorCanNotBeSentException;
import com.beganov.vendorservice.exception.VendorNotFoundException;
import com.beganov.vendorservice.mapper.VendorMapper;
import com.beganov.vendorservice.model.Vendor;
import com.beganov.vendorservice.repository.VendorRepository;
import com.beganov.vendorservice.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;
    private final RestTemplate restTemplate;

    @Autowired
    public VendorServiceImpl(@Qualifier("getRestTemplate") RestTemplate restTemplate, VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.restTemplate = restTemplate;
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    /**
     * Получение данных из "внешнего API"
     */
    @Override
    public List<VendorResponse> getVendorsFromExternalApi() {
        List<VendorRequest> vendorList = createFakeVendorRequests();

        if (vendorList.isEmpty()) {
            throw new VendorNotFoundException("No vendors found in the database to fetch");
        }

        // Преобразуем и сохраняем вендоров в БД
        List<Vendor> vendors = vendorList.stream()
                .map(vendorMapper::toEntity)
                .collect(Collectors.toList());
        vendorRepository.saveAll(vendors);

        // Преобразуем обратно в VendorResponse для возвращаемого ответа
        return vendors.stream()
                .map(vendorMapper::toResponse)
                .toList();
    }

    /**
     * Отправка данных во "внешний API"
     */
    @Override
    public String pushDataToExternalVendor(Long id) {
        // URL внешнего сервиса
        String url = "https://third-party-api.com/vendors";

        Vendor vendor = vendorRepository.findById(id).orElseThrow(() -> new VendorNotFoundException("No vendor found in the database"));
            try {
                restTemplate.postForObject(url, vendorMapper.toResponse(vendor), String.class);
            } catch (RestClientException ex) {
                System.err.println("Error while communicating with vendor API: " + ex.getMessage());
                throw new VendorCanNotBeSentException("Unable to push data to vendor");
            }
        return "Vendor with id " + id + " was sent to " + url;
    }

    /**
     * имитация данных, которые получаем из внешнего API
     */
    private List<VendorRequest> createFakeVendorRequests() {
        return List.of(
                new VendorRequest("Vendor 1", "Address 1", "Product 1", "Description 1"),
                new VendorRequest("Vendor 2", "Address 2", "Product 2", "Description 2"),
                new VendorRequest("Vendor 3", "Address 3", "Product 3", "Description 3"),
                new VendorRequest("Vendor 4", "Address 4", "Product 4", "Description 4"),
                new VendorRequest("Vendor 5", "Address 5", "Product 5", "Description 5")
        );
    }

}

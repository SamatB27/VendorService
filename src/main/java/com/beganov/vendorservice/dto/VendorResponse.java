package com.beganov.vendorservice.dto;

import lombok.Data;

@Data
public class VendorResponse {

    private Long id;
    private String name;
    private String address;
    private String product;
    private String description;

}

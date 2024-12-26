package com.beganov.vendorservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VendorResponse {

    private Long id;
    private String name;
    private String address;
    private String product;
    private String description;

}

package com.beganov.vendorservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String product;
    private String description;

}

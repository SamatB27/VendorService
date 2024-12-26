package com.beganov.vendorservice.exception;

public class VendorCanNotBeSentException extends RuntimeException {
    public VendorCanNotBeSentException(String message) {
        super(message);
    }
}

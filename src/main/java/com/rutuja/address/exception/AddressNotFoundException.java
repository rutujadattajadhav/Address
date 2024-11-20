package com.rutuja.address.exception;

public class AddressNotFoundException extends Exception {
    String addressNotFound;
    public AddressNotFoundException(String addressNotFound) {
        this.addressNotFound = addressNotFound;
    }
}

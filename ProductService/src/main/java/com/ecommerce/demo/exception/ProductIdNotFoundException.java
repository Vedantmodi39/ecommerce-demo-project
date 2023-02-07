package com.ecommerce.demo.exception;

public class ProductIdNotFoundException extends RuntimeException {

    public ProductIdNotFoundException(String message) {
        super(message);
    }
}

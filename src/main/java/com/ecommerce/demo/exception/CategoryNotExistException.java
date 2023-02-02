package com.ecommerce.demo.exception;

public class CategoryNotExistException extends RuntimeException{
    public CategoryNotExistException(String message) {
        super(message);
    }
}

package com.ecommerce.demo.exception;

public class ProductSkuNotFoundException extends RuntimeException{

    public ProductSkuNotFoundException(String msg)
    {
        super(msg);
    }
}

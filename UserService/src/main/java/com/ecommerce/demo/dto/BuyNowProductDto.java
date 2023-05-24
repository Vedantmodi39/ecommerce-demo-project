package com.ecommerce.demo.dto;

import com.ecommerce.demo.entity.Product;
import com.ecommerce.demo.entity.UserAddress;
import com.ecommerce.demo.entity.UserPayment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BuyNowProductDto {

    public int quantity;
    public UserAddress userAddress;
    public UserPayment userPayment;
    public Product product;
}

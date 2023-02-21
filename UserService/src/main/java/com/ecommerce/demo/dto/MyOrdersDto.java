package com.ecommerce.demo.dto;

import com.ecommerce.demo.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyOrdersDto {

    private int price;
    private int quantity;
    private String name;
    private String description;
    private String sku;
}

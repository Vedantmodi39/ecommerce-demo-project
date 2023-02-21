package com.ecommerce.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProductDto {
    private String name;
    private String description;
    private String sku;
    private double price;
    @JsonIgnore
    private int productCategoryId;
}

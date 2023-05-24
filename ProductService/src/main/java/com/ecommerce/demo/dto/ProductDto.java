package com.ecommerce.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private String name;
    private String description;
    private String sku;
    private double price;
    @JsonIgnore
    private int productCategoryId;
    @JsonIgnore
    private int productInventoryId;

}

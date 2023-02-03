package com.ecommerce.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

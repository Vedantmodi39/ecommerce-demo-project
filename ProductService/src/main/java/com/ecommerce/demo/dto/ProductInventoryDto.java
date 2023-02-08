package com.ecommerce.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventoryDto {

    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

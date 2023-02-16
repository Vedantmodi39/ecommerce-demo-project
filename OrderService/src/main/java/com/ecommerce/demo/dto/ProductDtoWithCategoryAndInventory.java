package com.ecommerce.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDtoWithCategoryAndInventory {

    private ProductDto productDto;
    private ProductCategoryDto productCategoryDto;
    private ProductInventoryDto productInventoryDto;

}

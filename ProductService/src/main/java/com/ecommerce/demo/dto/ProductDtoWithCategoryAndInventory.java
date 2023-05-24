package com.ecommerce.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDtoWithCategoryAndInventory {

    private ProductDto productDto;
    private ProductCategoryDto productCategoryDto;
    private ProductInventoryDto productInventoryDto;

}

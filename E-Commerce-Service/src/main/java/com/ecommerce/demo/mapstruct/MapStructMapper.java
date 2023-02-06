package com.ecommerce.demo.mapstruct;

import com.ecommerce.demo.dto.ProductDto;
import com.ecommerce.demo.dto.ProductInventoryDto;
import com.ecommerce.demo.entity.Product;
import com.ecommerce.demo.entity.ProductInventory;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    Product productDtoToProduct(ProductDto productDto);

    ProductInventory ProductInventoryDtoToProductInventory(ProductInventoryDto productInventoryDto);


}

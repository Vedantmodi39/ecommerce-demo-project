package com.ecommerce.demo.mapstruct;

import com.ecommerce.demo.dto.ProductCategoryDto;
import com.ecommerce.demo.dto.ProductDto;
import com.ecommerce.demo.dto.ProductDtoWithCategoryAndInventory;
import com.ecommerce.demo.dto.ProductInventoryDto;
import com.ecommerce.demo.entity.Product;
import com.ecommerce.demo.entity.ProductCategory;
import com.ecommerce.demo.entity.ProductInventory;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    Product productDtoToProduct(ProductDto productDto);

    ProductInventory ProductInventoryDtoToProductInventory(ProductInventoryDto productInventoryDto);


    ProductInventoryDto ProductInventoryToProductInventoryDto(ProductInventory productInventory);

    ProductDto ProductToProductDto(Product product);

    ProductCategoryDto ProductCategoryToProductCategoryDto(ProductCategory productCategory);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updateProductFromDto(ProductDto productDto, @MappingTarget Product product);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductInventory updateProductInventoryFromDto(ProductInventoryDto productInventoryDto, @MappingTarget ProductInventory productInventory);
}

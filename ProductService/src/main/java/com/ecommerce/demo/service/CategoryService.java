package com.ecommerce.demo.service;

import com.ecommerce.demo.entity.ProductCategory;
import com.ecommerce.demo.mapstruct.MapStructMapper;
import com.ecommerce.demo.repository.ProductCategoryRepository;
import dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    private final MapStructMapper mapStructMapper;

    public CategoryService(MapStructMapper mapStructMapper, ProductCategoryRepository productCategoryRepository) {
        this.mapStructMapper = mapStructMapper;
        this.productCategoryRepository=productCategoryRepository;
    }

    public Object addCategory(CategoryDto categoryDto) {
        ProductCategory productCategory = mapStructMapper.categoryDtoToProductCategory(categoryDto);
        productCategory.setCreatedAt(LocalDateTime.now());
        productCategory.setModifiedAt(LocalDateTime.now());
        productCategoryRepository.save(productCategory);
        return categoryDto;
    }
}

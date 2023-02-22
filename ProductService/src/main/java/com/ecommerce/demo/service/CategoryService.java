package com.ecommerce.demo.service;

import com.ecommerce.demo.entity.ProductCategory;
import com.ecommerce.demo.exception.CategoryAlreadyExistsException;
import com.ecommerce.demo.mapstruct.MapStructMapper;
import com.ecommerce.demo.repository.ProductCategoryRepository;
import dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    private final MapStructMapper mapStructMapper;

    public CategoryService(MapStructMapper mapStructMapper, ProductCategoryRepository productCategoryRepository) {
        this.mapStructMapper = mapStructMapper;
        this.productCategoryRepository=productCategoryRepository;
    }

    public Object addCategory(CategoryDto categoryDto) {
        Optional<ProductCategory> savedCategory = productCategoryRepository.findByName(categoryDto.getName());
        if(savedCategory.isPresent()){
            throw new CategoryAlreadyExistsException("Category Already Exists !");
        }
        ProductCategory productCategory = mapStructMapper.categoryDtoToProductCategory(categoryDto);
        productCategory.setCreatedAt(LocalDateTime.now());
        productCategory.setModifiedAt(LocalDateTime.now());
        productCategoryRepository.save(productCategory);
        return categoryDto;
    }
}

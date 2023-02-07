package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.ProductCategoryDto;
import com.ecommerce.demo.dto.ProductDto;
import com.ecommerce.demo.dto.ProductDtoWithCategoryAndInventory;
import com.ecommerce.demo.dto.ProductInventoryDto;
import com.ecommerce.demo.entity.Product;
import com.ecommerce.demo.entity.ProductCategory;
import com.ecommerce.demo.entity.ProductInventory;
import com.ecommerce.demo.exception.CategoryNotExistException;
import com.ecommerce.demo.exception.ProductAlreadyExistException;
import com.ecommerce.demo.exception.ProductIdNotFoundException;
import com.ecommerce.demo.mapstruct.MapStructMapper;
import com.ecommerce.demo.repository.ProductCategoryRepository;
import com.ecommerce.demo.repository.ProductRepository;
import com.ecommerce.demo.utility.RecordCreationUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    public ProductService(ProductCategoryRepository productCategoryRepository, RecordCreationUtility recordCreationUtility, MapStructMapper mapStructMapper, ProductRepository productRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.recordCreationUtility = recordCreationUtility;
        this.mapStructMapper = mapStructMapper;
        this.productRepository = productRepository;
    }

    private ProductCategoryRepository productCategoryRepository;

    private final RecordCreationUtility recordCreationUtility;

    private final MapStructMapper mapStructMapper;

    private ProductRepository productRepository;


    public Object addProduct(ProductDtoWithCategoryAndInventory productDtoWithCategoryAndInventory) {


        Optional<Product> product1 = productRepository.findBySku(productDtoWithCategoryAndInventory.getProductDto().getSku());
        if (product1.isPresent()) {
            throw new ProductAlreadyExistException(productDtoWithCategoryAndInventory.getProductDto().getSku() + " ");
        } else {
            Optional<ProductCategory> productCategory = productCategoryRepository.findByName(productDtoWithCategoryAndInventory.getProductCategoryDto().getName());
            if (productCategory.isPresent()) {
                Product product = mapStructMapper.productDtoToProduct(productDtoWithCategoryAndInventory.getProductDto());
                product.setProductCategory(productCategory.get());
                BeanUtils.copyProperties(recordCreationUtility.putNewRecordInformation(), product);

                ProductInventoryDto productInventoryDto = new ProductInventoryDto();
                //BeanUtils.copyProperties(recordCreationUtility.putNewRecordInformation(),productInventoryDto);
                productInventoryDto.setModifiedAt(LocalDateTime.now());
                productInventoryDto.setCreatedAt(LocalDateTime.now());

                System.out.println(productInventoryDto.getCreatedAt());
                product.setProductInventory(mapStructMapper.ProductInventoryDtoToProductInventory(productDtoWithCategoryAndInventory.getProductInventoryDto()));

                System.out.println(product.getProductInventory().getCreatedAt());
                productRepository.save(product);

            } else {
                throw new CategoryNotExistException(productDtoWithCategoryAndInventory.getProductCategoryDto().getName() + " ");
            }
        }
        return productDtoWithCategoryAndInventory;
    }

    public ProductDtoWithCategoryAndInventory getProduct(int productId) {
        Optional<Product> product =productRepository.findById(productId);
        if(product.isPresent())
        {
            ProductDto productDto=mapStructMapper.ProductToProductDto(product.get());
            ProductInventoryDto productInventoryDto=mapStructMapper.ProductInventoryToProductInventoryDto(product.get().getProductInventory());
            ProductCategoryDto productCategoryDto=mapStructMapper.ProductCategoryToProductCategoryDto(product.get().getProductCategory());
           return new ProductDtoWithCategoryAndInventory(productDto,productCategoryDto,productInventoryDto);
        }
        else
        {
            throw new ProductIdNotFoundException(productId+" ");
        }

    }
}

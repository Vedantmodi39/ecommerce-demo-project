package com.ecommerce.demo.service;

import com.ecommerce.demo.entity.Product;
import com.ecommerce.demo.entity.ProductCategory;
import com.ecommerce.demo.exception.CategoryNotExistException;
import com.ecommerce.demo.exception.ProductAlreadyExistException;
import com.ecommerce.demo.repository.ProductCategoryRepository;
import com.ecommerce.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    ProductRepository productRepository;

    public Object addProduct(Product product) {

        Optional<Product> product1 = productRepository.findBySku(product.getSku());
        if (product1.isPresent())
        {
            throw new ProductAlreadyExistException(product.getSku( ) +" ");
        }
        else {
            Optional<ProductCategory> productCategory=productCategoryRepository.findByName(product.getProductCategory().getName());
            if(productCategory.isPresent())
            {
                product.setCreatedAt(LocalDateTime.now());
                product.setProductCategory(productCategory.get());
                product.setProductInventory(product.getProductInventory());
                product.getProductInventory().setCreatedAt(LocalDateTime.now());
                productRepository.save(product);
            }
            else {
                throw new CategoryNotExistException(product.getProductCategory().getName()+" ");
            }
        }
        return product;
    }
}

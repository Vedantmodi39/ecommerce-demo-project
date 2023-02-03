package com.ecommerce.demo.repository;

import com.ecommerce.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {


    Optional<Product> findBySku(String sku);
}

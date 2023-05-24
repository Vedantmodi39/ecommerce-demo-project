package com.ecommerce.demo.repository;

import com.ecommerce.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {


    Optional<Product> findBySku(String sku);

    @Query("SELECT p FROM Product p WHERE "+
    "p.name LIKE CONCAT ('%',:query,'%')"+
    "OR p.description LIKE CONCAT ('%',:query,'%')")
    List<Product> searchProducts(String query);
}

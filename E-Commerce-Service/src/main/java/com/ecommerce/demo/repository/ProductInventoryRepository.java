package com.ecommerce.demo.repository;

import com.ecommerce.demo.entity.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInventoryRepository extends JpaRepository<ProductInventory ,Integer> {
}

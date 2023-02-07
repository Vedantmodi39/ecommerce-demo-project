package com.ecommerce.demo.repository;

import com.ecommerce.demo.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Query(value = "SELECT ci FROM Users u JOIN u.cartItems ci JOIN ci.product p WHERE u.username = ?1 AND p.sku = ?2")
    CartItem findByUserAndProduct(String username, String sku);
}

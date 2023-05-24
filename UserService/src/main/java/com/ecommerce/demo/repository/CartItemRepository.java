package com.ecommerce.demo.repository;

import com.ecommerce.demo.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
   @Query(value ="SELECT ci FROM Users u JOIN u.cartItems ci where u.id = ?1")
    List<CartItem> findCartInUser(int id);

//    @Modifying
//    @Query("delete from CartItem ci where ci.id = ?1")
//    void deleteCartItemById(Integer id);
}
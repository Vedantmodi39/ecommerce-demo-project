package com.ecommerce.demo.repository;

import com.ecommerce.demo.entity.OrderDetails;
import com.ecommerce.demo.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Integer> {
    @Query("select o.orderItems from OrderDetails o where o.user.id = ?1")
    List<OrderItems> findByUserId(int id);


}

package com.ecommerce.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private String sku;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private ProductCategory productCategory ;

    @OneToOne(cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    private ProductInventory productInventory;

    private double price;
    private boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;

}

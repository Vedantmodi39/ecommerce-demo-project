package com.ecommerce.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    private String name;

    private String desc;

    private String sku;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private ProductCategory productCategory ;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private ProductInventory productInventory;

    private double price;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;

}

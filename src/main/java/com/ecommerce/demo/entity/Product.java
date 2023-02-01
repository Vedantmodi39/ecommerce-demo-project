package com.ecommerce.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private ProductCategory productCategory ;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private ProductInventory productInventory;
}

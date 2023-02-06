package com.ecommerce.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Product product ;


}

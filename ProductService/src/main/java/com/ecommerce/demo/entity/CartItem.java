package com.ecommerce.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of={"id"})
public class CartItem {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    private int quantity;
    private LocalDateTime createdAt;
    private double totalPrice;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name="product_id_fk",referencedColumnName = "id")
    private Product product;


}

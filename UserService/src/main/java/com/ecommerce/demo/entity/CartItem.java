package com.ecommerce.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of={"id"})
public class CartItem {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;
    private int quantity;
    private int totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="product_id_fk",referencedColumnName = "id")
    private Product product ;


}

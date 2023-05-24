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
public class OrderDetails {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;



    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Users user ;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private PaymentDetails paymentDetails;

   @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
   private UserPayment userPayments;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private UserAddress userAddress;

    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "orderDetails_FK",referencedColumnName = "id")
    private List<OrderItems> orderItems;


}

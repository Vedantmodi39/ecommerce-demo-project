package com.ecommerce.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private List<UserAddress> userAddresses ;

    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private List<UserPayment> userPayments ;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name="user_id_fk",referencedColumnName = "id")
    private List<CartItem> cartItems ;
}

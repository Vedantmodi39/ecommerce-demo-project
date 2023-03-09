package com.ecommerce.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserPayment {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    private String paymentType;
    private String provider;
    private String accountNo;
    private LocalDate expiry;

    public UserPayment(int id, String paymentType, String provider, String accountNo, String expiry) {

    }
}

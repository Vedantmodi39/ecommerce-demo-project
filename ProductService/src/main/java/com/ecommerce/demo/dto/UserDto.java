package com.ecommerce.demo.dto;

import com.ecommerce.demo.entity.CartItem;
import com.ecommerce.demo.entity.UserAddress;
import com.ecommerce.demo.entity.UserPayment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String email;
    private List<UserAddress> userAddressesList;
    private List<UserPayment> userPaymentsList;
    private List<CartItem> cartItemsList;

}
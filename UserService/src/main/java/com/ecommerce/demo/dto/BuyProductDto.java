package com.ecommerce.demo.dto;

import com.ecommerce.demo.entity.CartItem;
import com.ecommerce.demo.entity.UserAddress;
import com.ecommerce.demo.entity.UserPayment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyProductDto {
   private UserPayment userPayment;
   //private UserAddress userAddress;
   private Set<CartItem> cartItemSet;
}

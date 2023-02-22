package com.ecommerce.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCartItemDto {
    private int quantity;
    private double totalPrice;
    private CartProductDto cartProductDto;
}

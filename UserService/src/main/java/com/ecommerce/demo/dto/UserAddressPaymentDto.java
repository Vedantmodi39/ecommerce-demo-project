package com.ecommerce.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressPaymentDto {
    private UserDto userDto;
    private UserAddressDto userAddressDto;
    private UserPaymentDto userPaymentDto;
}

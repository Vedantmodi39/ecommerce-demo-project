package com.ecommerce.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressPaymentDto {
    private UserDto userDto;
    private List<UserAddressDto> userAddressDto;
    private List<UserPaymentDto> userPaymentDto;
}

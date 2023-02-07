package com.ecommerce.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressDto {
    private String address;
    private String city;
    private String state;
    private String county;
    private String zipCode;
    private String mobileNo;
}

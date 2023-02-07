package com.ecommerce.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String email;

}

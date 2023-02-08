package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.UserAddressPaymentDto;
import com.ecommerce.demo.dto.UserDto;

public interface UserService {
    public Object addUser(UserDto userDto);
    public Object getUser(int userId);
    public Object deleteUser(int userId);
    public UserDto updateUser(UserDto userDto , int id);

}

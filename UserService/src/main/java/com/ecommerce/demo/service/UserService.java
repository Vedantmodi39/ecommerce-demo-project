package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.UserAddressPaymentDto;

public interface UserService {
    public Object addUser(UserAddressPaymentDto userAddressPaymentDto);
    public Object getUser(int userId);
    public Object deleteUser(int userId);
}

package com.ecommerce.demo.mapstruct;


import com.ecommerce.demo.dto.UserAddressDto;
import com.ecommerce.demo.dto.UserAddressPaymentDto;
import com.ecommerce.demo.dto.UserDto;
import com.ecommerce.demo.dto.UserPaymentDto;
import com.ecommerce.demo.entity.*;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    Users userPaymentAddressDtoToUser(UserAddressPaymentDto userAddressPaymentDto);


    Users userDtoToUser(UserDto userDto);

    UserPayment userPaymentDtoToUserPayment(UserPaymentDto userPaymentDto);

    UserAddress userAddressDtoToUserAddress(UserAddressDto userAddressDto);

    UserDto UserToUserDto(Users users);

    



    UserAddressDto UserAddressToUserAddressDto(UserAddress userAddress);

    UserPaymentDto UserPaymentToUserPaymentDto(UserPayment userPayment);
}

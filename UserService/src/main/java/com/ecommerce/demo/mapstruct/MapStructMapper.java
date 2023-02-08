package com.ecommerce.demo.mapstruct;


import com.ecommerce.demo.dto.UserAddressDto;
import com.ecommerce.demo.dto.UserAddressPaymentDto;
import com.ecommerce.demo.dto.UserDto;
import com.ecommerce.demo.dto.UserPaymentDto;
import com.ecommerce.demo.entity.*;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    Users userPaymentAddressDtoToUser(UserAddressPaymentDto userAddressPaymentDto);

    @Mapping(source = "userDto.userAddressesList" ,target = "userAddresses")
    @Mapping(source = "userDto.userPaymentsList" , target="userPayments")
    Users userDtoToUser(UserDto userDto);

    UserPayment userPaymentDtoToUserPayment(UserPaymentDto userPaymentDto);

    UserAddress userAddressDtoToUserAddress(UserAddressDto userAddressDto);
    @InheritInverseConfiguration(name = "userDtoToUser")
    UserDto UserToUserDto(Users users);

    



    UserAddressDto UserAddressToUserAddressDto(UserAddress userAddress);

    UserPaymentDto UserPaymentToUserPaymentDto(UserPayment userPayment);
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "userDto.userAddressesList" ,target = "userAddresses")
    @Mapping(source = "userDto.userPaymentsList" , target="userPayments")
    Users updateUserfromDto(UserDto userDto, @MappingTarget Users users);
}

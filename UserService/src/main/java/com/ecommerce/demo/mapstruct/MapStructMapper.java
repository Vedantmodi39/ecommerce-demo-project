package com.ecommerce.demo.mapstruct;


import com.ecommerce.demo.dto.UserAddressPaymentDto;
import com.ecommerce.demo.dto.UserDto;
import com.ecommerce.demo.entity.Product;
import com.ecommerce.demo.entity.ProductInventory;
import com.ecommerce.demo.entity.Users;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    Users userPaymentAddressDtoToUser(UserAddressPaymentDto userAddressPaymentDto);

}

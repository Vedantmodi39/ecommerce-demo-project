package com.ecommerce.demo.mapstruct;



import com.ecommerce.demo.dto.UserDto;
import com.ecommerce.demo.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    @Mapping(source = "userDto.userAddressesList" ,target = "userAddresses")
    @Mapping(source = "userDto.userPaymentsList" , target="userPayments")
    Users userDtoToUser(UserDto userDto);

    @InheritInverseConfiguration(name = "userDtoToUser")
    UserDto UserToUserDto(Users users);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "userDto.userAddressesList" ,target = "userAddresses")
    @Mapping(source = "userDto.userPaymentsList" , target="userPayments")
    Users updateUserfromDto(UserDto userDto, @MappingTarget Users users);
}

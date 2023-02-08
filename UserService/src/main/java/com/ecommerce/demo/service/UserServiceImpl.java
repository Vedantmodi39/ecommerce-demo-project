package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.UserAddressDto;
import com.ecommerce.demo.dto.UserAddressPaymentDto;
import com.ecommerce.demo.dto.UserDto;
import com.ecommerce.demo.dto.UserPaymentDto;
import com.ecommerce.demo.entity.UserAddress;
import com.ecommerce.demo.entity.UserPayment;
import com.ecommerce.demo.entity.Users;
import com.ecommerce.demo.exception.UserAlreadyExistException;
import com.ecommerce.demo.exception.UserNotFoundException;
import com.ecommerce.demo.mapstruct.MapStructMapper;
import com.ecommerce.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

    private final MapStructMapper mapStructMapper;

    public UserServiceImpl(UserRepository userRepository, MapStructMapper mapStructMapper) {
        this.userRepository = userRepository;
        this.mapStructMapper = mapStructMapper;
    }

    public Object addUser(UserDto userDto)  {
        Optional<Users> user = userRepository.findByEmail(userDto.getEmail());

        if(user.isPresent()){
            throw new UserAlreadyExistException(userDto.getEmail()+"");
        }else {

            Users users = mapStructMapper.userDtoToUser(userDto);
//            users.setUserPayments(userAddressPaymentDto.getUserPaymentDto().stream().map(mapStructMapper::userPaymentDtoToUserPayment).collect(Collectors.toList()));
//            users.setUserAddresses(userAddressPaymentDto.getUserAddressDto().stream().map(mapStructMapper::userAddressDtoToUserAddress).collect(Collectors.toList()));
            users.setCreatedAt(LocalDateTime.now());
            users.setModifiedAt(LocalDateTime.now());

            userRepository.save(users);
            return userDto;
        }
    }

    public UserDto getUser(int userId){
        Optional<Users> savedUser = userRepository.findById(userId);
        if(savedUser.isPresent()){
            UserDto userDto = mapStructMapper.UserToUserDto(savedUser.get());
//            List<UserAddressDto> userAddressDtoList = new ArrayList<>();
//            for(UserAddress userAddress : savedUser.get().getUserAddresses()){
//             UserAddressDto userAddressDto=mapStructMapper.UserAddressToUserAddressDto(userAddress);
//             userAddressDtoList.add(userAddressDto);
//            }
//            List<UserPaymentDto> userPaymentList = new ArrayList<>();
//            for(UserPayment userPayment : savedUser.get().getUserPayments()){
//                UserPaymentDto userPaymentDto=mapStructMapper.UserPaymentToUserPaymentDto(userPayment);
//                userPaymentList.add(userPaymentDto);
//            }

//            UserPayment userPaymentDto = mapStructMapper.UserPaymentToUserPaymentDto(savedUser.get().getUserPayments());

            return userDto;
        }
        else {
            throw new UserNotFoundException("User Not Found !!");
        }
    }

    public Object deleteUser(int userId){
        Optional<Users> user1 = userRepository.findById(userId);
        if(user1.isEmpty()){
            throw new UserNotFoundException("User Not Found !!");
        }else
            userRepository.deleteById(userId);
        return "User Deleted";
    }

    public UserDto updateUser(UserDto userDto , int id){
        Optional<Users> newUser = userRepository.findById(id);
        if(newUser.isPresent()){
            Users users = mapStructMapper.updateUserfromDto(userDto , newUser.get());
            userRepository.save(users);
            return userDto;
        }else {
            throw new UserNotFoundException("USer Not found with ID :"+userDto.getId());
        }
    }
}

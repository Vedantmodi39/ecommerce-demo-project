package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.UserAddressPaymentDto;
import com.ecommerce.demo.dto.UserPaymentDto;
import com.ecommerce.demo.entity.UserPayment;
import com.ecommerce.demo.entity.Users;
import com.ecommerce.demo.exception.UserAlreadyExistException;
import com.ecommerce.demo.exception.UserNotFoundException;
import com.ecommerce.demo.mapstruct.MapStructMapper;
import com.ecommerce.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

    private final MapStructMapper mapStructMapper;

    public UserServiceImpl(UserRepository userRepository, MapStructMapper mapStructMapper) {
        this.userRepository = userRepository;
        this.mapStructMapper = mapStructMapper;
    }

    public Object addUser(UserAddressPaymentDto userAddressPaymentDto)  {
        Optional<Users> user = userRepository.findByEmail(userAddressPaymentDto.getUserDto().getEmail());

        if(user.isPresent()){
            throw new UserAlreadyExistException("User Already Exists !");
        }else {

            Users users = mapStructMapper.userDtoToUser(userAddressPaymentDto.getUserDto());
            ArrayList<UserPayment> userPaymentArrayList = new ArrayList<>();

            for(int i = 0; i >=  userAddressPaymentDto.getUserPaymentDto().size() ; i++) {
//                userPaymentArrayList.add(userAddressPaymentDto.getUserPaymentDto())
                users.setUserPayments(userPaymentArrayList);
            }
            users.setCreatedAt(LocalDateTime.now());
            users.setModifiedAt(LocalDateTime.now());

//            users.setUserAddresses(userDto.getUserAddresses());
//            users.setUserPayments(userDto.getUserPayments());
//            users.setCartItems(userDto.getCartItems());
            userRepository.save(users);
            return user;
        }
    }

    public Object getUser(int userId){
        Optional<Users> user1 = userRepository.findById(userId);
        if(user1.isEmpty()){
            throw new UserNotFoundException("User Not Found !!");
        }
        else
            return user1;
    }

    public Object deleteUser(int userId){
        Optional<Users> user1 = userRepository.findById(userId);
        if(user1.isEmpty()){
            throw new UserNotFoundException("User Not Found !!");
        }else
            userRepository.deleteById(userId);
        return "User Deleted";
    }
}

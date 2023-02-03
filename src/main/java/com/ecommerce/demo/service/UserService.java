package com.ecommerce.demo.service;

import com.ecommerce.demo.entity.Users;
import com.ecommerce.demo.exception.UserAlreadyExistException;
import com.ecommerce.demo.exception.UserNotFoundException;
import com.ecommerce.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Object addUser(Users user)  {
        Optional<Users> user1 = userRepository.findById(user.getId());

        if(user1.isPresent()){
            throw new UserAlreadyExistException("User Already Exists !");
        }else {

            user.setCreatedAt(LocalDateTime.now());
            user.setModifiedAt(LocalDateTime.now());
            user.setUserAddresses(user.getUserAddresses());
            user.setUserPayments(user.getUserPayments());
            user.setCartItems(user.getCartItems());
            userRepository.save(user);
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

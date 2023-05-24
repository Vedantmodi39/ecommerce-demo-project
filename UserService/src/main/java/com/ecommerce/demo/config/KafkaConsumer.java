package com.ecommerce.demo.config;

import com.ecommerce.demo.entity.CartItem;
import com.ecommerce.demo.entity.Users;
import com.ecommerce.demo.exception.UserNotFoundException;
import com.ecommerce.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaConsumer {

    @Autowired
    UserRepository userRepository;

    @KafkaListener(topics = "user_topic", groupId = "user-result-group")
    @SendTo
    public Users handle(int userId){

       Users user =  userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not Found !"));
        System.out.println("User in consumer ::" +user);
        return user;
    }
    @KafkaListener(topics = "user_save", groupId = "user-save-group")
    @SendTo
    public Users handleSaveUser(Users user){
        System.out.println("Before ::" +user.toString());
        Users abc = userRepository.save(user);

        System.out.println("user saved :: "+abc.getCartItems());


        return abc;
    }
}

package com.ecommerce.demo.config;

import com.ecommerce.demo.entity.CartItem;
import com.ecommerce.demo.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaConsumer {

    @Autowired
    CartItemRepository cartItemRepository;

    @KafkaListener(topics= "cart_item" , groupId = "cart-fetch")
    @SendTo
    public List<CartItem> fetchCart(int id){

        List<CartItem> cartItemList = cartItemRepository.findCartInUser(id);
        return cartItemList;

    }
}

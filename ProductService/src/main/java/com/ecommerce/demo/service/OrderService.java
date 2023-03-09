package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.CartItemDto;
import com.ecommerce.demo.dto.CartProductDto;
import com.ecommerce.demo.dto.GetCartItemDto;
import com.ecommerce.demo.entity.CartItem;
import com.ecommerce.demo.entity.Product;
import com.ecommerce.demo.entity.Users;
import com.ecommerce.demo.exception.ProductSkuNotFoundException;
import com.ecommerce.demo.exception.UserNotFoundException;
import com.ecommerce.demo.repository.CartItemRepository;
import com.ecommerce.demo.repository.ProductRepository;
import com.ecommerce.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class    OrderService {
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    @Value("${spring.kafka.request.topic}")
    private String requestTopic;

    @Autowired
    private ReplyingKafkaTemplate<String, Object, Users> replyingKafkaTemplate;

    public Set<CartItemDto> updateCart(Set<CartItemDto> cartItemDtos, int userId){

        Users user = userRepository.findById(userId).orElse(null);
        if(user == null)
            return null;


        List<CartItem> cartItemList = user.getCartItems();
        Set<Product> productSet = cartItemList.stream().map(CartItem::getProduct).collect(Collectors.toSet());


        List<CartItem> cartItemListNew = new ArrayList<>();

        for(CartItemDto cartItemDto : cartItemDtos){
            Product product =  productRepository.findBySku(cartItemDto.getProductSku()).orElseThrow(() -> new ProductSkuNotFoundException(cartItemDto.getProductSku()+""));

            CartItem cartItem;
            if(productSet.contains(product)){
                cartItem = cartItemRepository.findByUserAndProduct(user.getUsername(), cartItemDto.getProductSku());
                cartItem.setModifiedAt(LocalDateTime.now());
                cartItem.setQuantity(cartItemDto.getQuantity());
                if(cartItemDto.getQuantity() == 0)
                    cartItem.setDeletedAt(LocalDateTime.now());
                else

                    cartItem.setDeletedAt(null);
            }else{
                cartItem = new CartItem();
                cartItem.setCreatedAt(LocalDateTime.now());
                cartItem.setTotalPrice(cartItemDto.getQuantity()* product.getPrice());
                cartItem.setQuantity(cartItemDto.getQuantity());
            }
            cartItem.setProduct(product);
            cartItemListNew.add(cartItem);
        }
        cartItemListNew.addAll(cartItemList);
        user.setCartItems(cartItemListNew);
        userRepository.save(user);
        return cartItemDtos;
    }

    public Set<GetCartItemDto> getCart(int userId) throws InterruptedException, ExecutionException {

        ProducerRecord<String, Object> record = new ProducerRecord<>(requestTopic, null, "user001", userId);
        RequestReplyFuture<String, Object, Users> future = replyingKafkaTemplate.sendAndReceive(record);
        ConsumerRecord<String, Users> response = future.get();

        Users user = response.value();

        if(user != null){
            Set<GetCartItemDto> getCartItemDtoSet = new HashSet<>();

            List<CartItem> cartItemList = user.getCartItems();

            for(CartItem cartItem : cartItemList){
                Product product = cartItem.getProduct();
                GetCartItemDto getCartItemDto = new GetCartItemDto();
                getCartItemDto.setQuantity(cartItem.getQuantity());
                getCartItemDto.setTotalPrice(cartItem.getTotalPrice());

                CartProductDto cartProductDto = new CartProductDto();
                cartProductDto.setName(product.getName());
                cartProductDto.setPrice(product.getPrice());
                cartProductDto.setSku(product.getSku());
                cartProductDto.setDescription(product.getDescription());
                cartProductDto.setProductCategoryName(product.getProductCategory().getName());
                getCartItemDto.setCartProductDto(cartProductDto);

                getCartItemDtoSet.add(getCartItemDto);
            }

            return  getCartItemDtoSet;
        }
        else{
            throw new UserNotFoundException("User Not Found");
        }
    }
}

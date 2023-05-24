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
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class    OrderService {
    private final CartItemRepository cartItemRepository;
//    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Value("${spring.kafka.request.topic}")
    private String requestTopic;

    @Value("${spring.kafka.request.topic2}")
    private String saveRequestTopic;

    @Autowired
    private KafkaTemplate<String, Users> kafkaTemplate;
    @Autowired
    private ReplyingKafkaTemplate<String, Object, Users> replyingKafkaTemplate;

    @Autowired
    private ReplyingKafkaTemplate<String,Object,Users> savereplyingKafkaTemplate;

    @Autowired
    KafkaTemplate<String, Users> KafkaTemplate;

    public List<GetCartItemDto> updateCart(Set<CartItemDto> cartItemDtos, int userId) throws InterruptedException, ExecutionException, TimeoutException {

//
        ProducerRecord<String, Object> record = new ProducerRecord<>(requestTopic, null, "user001", userId);
        RequestReplyFuture<String, Object, Users> future = replyingKafkaTemplate.sendAndReceive(record);
        ConsumerRecord<String, Users> response = future.get(10, TimeUnit.SECONDS);

        Users user = response.value();
        if(user == null)
            return null;


        List<CartItem> cartItemList = user.getCartItems();
        Set<CartItem> cartItemSet = new HashSet<>(cartItemList);
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
            if(!cartItemListNew.contains(cartItem)) {
                cartItemListNew.add(cartItem);
                cartItemList.remove(cartItem);
            }
//            cartItemList.forEach(c ->{
//                if(c.equals(cartItem)){
//                    c=cartItem;
//                }
//            });
                cartItemSet.add(cartItem);

        }
        cartItemListNew.addAll(cartItemList);

        user.setCartItems(cartItemListNew);
//        userRepository.save(user);

//        Message<Users> message = MessageBuilder
//                .withPayload(user)
//                .setHeader(KafkaHeaders.TOPIC, saveRequestTopic)
//                .build();
//        System.out.println("message :" +message);
//        ProducerRecord<String, Object> saverecord = new ProducerRecord<>(saveRequestTopic, null, "usersave01",user);
//        RequestReplyFuture<String, Object, Users> futuresave = savereplyingKafkaTemplate.sendAndReceive(saverecord );
//        ConsumerRecord<String, Users> responseFromUserservice = futuresave.get(10, TimeUnit.SECONDS);

//        System.out.println("cartResponse ::" +responseFromUserservice.value());
        KafkaTemplate.send(saveRequestTopic,user);

        logger.info("Message sent "+user);

//        Users users = responseFromUserservice.value();

        List<CartItem> cartResponse = user.getCartItems();

        List<GetCartItemDto> updatedCart = new ArrayList<>();

        for(CartItem cartItem : cartResponse){
            Product product =  cartItem.getProduct();
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

            updatedCart.add(getCartItemDto);
        }


        return updatedCart;

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
            throw new UserNotFoundException("User Not Found Please Enter Valid UserId !");
        }
    }
}

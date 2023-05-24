package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.BuyNowProductDto;
import com.ecommerce.demo.dto.BuyProductDto;
import com.ecommerce.demo.dto.MyOrdersDto;
import com.ecommerce.demo.entity.*;
import com.ecommerce.demo.exception.UserNotFoundException;
import com.ecommerce.demo.mapstruct.MapStructMapper;
import com.ecommerce.demo.repository.*;
import jakarta.transaction.Transactional;
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

@Service
public class BuyProductService {

    @Autowired
    ProductInventoryRepository productInventoryRepository;

    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
     MapStructMapper mapStructMapper;
    @Autowired
    UserPaymentRepository userPaymentRepository;

    @Autowired
    OrderDetailsRepository orderDetailsRepository;
    @Autowired
    OrderItemsRepository orderItemsRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    ReplyingKafkaTemplate<String,Object,List<CartItem>> replyingKafkaTemplate;

    @Value("${spring.kafka.request.topic3}")
    private String replyTopic;

    @Transactional
    public String buyProducts(int id, BuyProductDto buyProductDto) throws ExecutionException, InterruptedException {

        Optional<Users> user = userRepository.findById(id);
        if (user.isPresent()) {

            OrderDetails orderDetails = new OrderDetails();

            List<OrderItems> orderItemsList = new ArrayList<>();

            UserPayment userPaymentRequestBody = buyProductDto.getUserPayment();
            UserAddress userAddressRequestBody = buyProductDto.getUserAddress();
            Users users = userRepository.findByUserPaymentsIdAndId(userPaymentRequestBody.getId(),id,userAddressRequestBody.getId());
            System.out.println("fetched user :: "+users);
            if (users != null) {
                for (UserPayment userPayment : users.getUserPayments())
                    if (userPayment.getId() == userPaymentRequestBody.getId()) {
                        orderDetails.setUserPayments(userPayment);
                    }
                for (UserAddress userAddress : users.getUserAddresses()){
                    if(userAddress.getId() == userAddressRequestBody.getId()){
                        orderDetails.setUserAddress(userAddress);
                    }
                }
                orderDetails.setUser(users);

            } else {
                throw new IllegalArgumentException("User Payment Or Address Does Not Exist");
            }


            ProducerRecord<String,Object> record =new ProducerRecord<>(replyTopic,null,"cart001",id);
            RequestReplyFuture<String,Object,List<CartItem>>future =replyingKafkaTemplate.sendAndReceive(record);
            ConsumerRecord<String,List<CartItem>> response = future.get();

            List<CartItem> cartItemList = response.value();
//            List<CartItem> cartItem1 = cartItemRepository.findCartInUser(id);
            for (CartItem cartItem : cartItemList) {

                        CartItem cartItem3 = cartItemRepository.findById(cartItem.getId()).orElseThrow(() -> new IllegalArgumentException("CardId Not Found"));
                        ProductInventory productInventory = cartItem3.getProduct().getProductInventory();
                        int productQuantity = productInventory.getQuantity() - cartItem3.getQuantity();
                        if (productQuantity < 0) {
                            throw new IllegalArgumentException("Product Inventory Has No Sufficient Quantity");
                        }
                        productInventory.setQuantity(productQuantity);

                        Product product = cartItem3.getProduct();

                        OrderItems orderItems = new OrderItems();
                        orderItems.setProduct(product);
                        orderItems.setQuantity(cartItem3.getQuantity());
                        orderItems.setPrice(cartItem3.getTotalPrice());
                        orderItemsList.add(orderItems);

                        productInventoryRepository.save(productInventory);
                        cartItemRepository.deleteById(cartItem.getId());

                    }


            orderDetails.setOrderItems(orderItemsList);
            orderDetails.setCreatedAt(LocalDateTime.now());
            orderDetails.setModifiedAt(LocalDateTime.now());
            orderDetailsRepository.save(orderDetails);
            int totalPrice=0;
            for(OrderItems orderItems : orderItemsList){
                totalPrice+=orderItems.getPrice();
            }
            System.out.println("Total Price Order :: "+totalPrice);

            String body = "Hello "+users.getFirstName() +" Your Order Has Been Placed For Rs ."+totalPrice + " Will Be Delivered To : " +orderDetails.getUserAddress().getAddress() +" Within 1 Week . ";

            emailService.sendMail(users.getEmail(),"demodevnick5000@gmail.com", "Order Placed",body);

        } else {
            throw new UserNotFoundException("User Not Found");
        }
        return "Buy Products Successfully";
    }

    public List<MyOrdersDto> getMyOrders(int id) {

        Optional<Users> user = userRepository.findById(id);
        List<MyOrdersDto> myOrdersDtoList = new ArrayList<>();
        if (user.isPresent()) {

            List<OrderItems> orderItemsList = orderDetailsRepository.findByUserId(id);

            for (OrderItems orderItems : orderItemsList) {
                MyOrdersDto myOrdersDto;
                myOrdersDto = mapStructMapper.OrderItemsToMyOrdersDto(orderItems);
                myOrdersDto.setName(orderItems.getProduct().getName());
                myOrdersDto.setSku(orderItems.getProduct().getSku());
                myOrdersDto.setDescription(orderItems.getProduct().getDescription());
                myOrdersDtoList.add(myOrdersDto);
            }
        } else {
            throw new UserNotFoundException("User Not Found");
        }
        return myOrdersDtoList;

    }


    public String buyNowProduct(int userId, BuyNowProductDto buyNowProductDto) {
        Optional<Users> users  =userRepository.findById(userId);
        if(users.isPresent()){
            OrderDetails orderDetails = new OrderDetails();

            List<OrderItems> orderItemsList = new ArrayList<>();

            UserPayment userPaymentRequestBody = buyNowProductDto.getUserPayment();
            UserAddress userAddressRequestBody = buyNowProductDto.getUserAddress();
            Users user = userRepository.findByUserPaymentsIdAndId(userPaymentRequestBody.getId(),userId,userAddressRequestBody.getId());

            if (user != null) {
                for (UserPayment userPayment : user.getUserPayments())
                    if (userPayment.getId() == userPaymentRequestBody.getId()) {
                        orderDetails.setUserPayments(userPayment);
                    }
                for (UserAddress userAddress : user.getUserAddresses()){
                    if(userAddress.getId() == userAddressRequestBody.getId()){
                        orderDetails.setUserAddress(userAddress);
                    }
                }
                orderDetails.setUser(user);

            } else {
                throw new IllegalArgumentException("User Payment Or Address Does Not Exist");
            }

            Product product = productRepository.findById(buyNowProductDto.getProduct().getId()).orElseThrow(() -> new IllegalArgumentException("Product Not Found"));
            ProductInventory productInventory = product.getProductInventory();
            int productQuantity = productInventory.getQuantity() - buyNowProductDto.getQuantity();
            if (productQuantity < 0) {
                throw new IllegalArgumentException("Product Inventory Has No Sufficient Quantity");
            }
            productInventory.setQuantity(productQuantity);
            productInventoryRepository.save(productInventory);

            OrderItems orderItems = new OrderItems();
            orderItems.setProduct(product);
            orderItems.setQuantity(buyNowProductDto.getQuantity());
            orderItems.setPrice(buyNowProductDto.getQuantity() * buyNowProductDto.getProduct().getPrice());
            orderItems.setCreatedAt(LocalDateTime.now());
            orderItems.setModifiedAt(LocalDateTime.now());

            orderItemsList.add(orderItems);

           orderDetails.setOrderItems(orderItemsList);
           orderDetails.setCreatedAt(LocalDateTime.now());
           orderDetails.setModifiedAt(LocalDateTime.now());

           orderDetailsRepository.save(orderDetails);

        }else {
            throw new UserNotFoundException("User Not Found.");
        }
        return "Order Placed Successfully";
    }

}

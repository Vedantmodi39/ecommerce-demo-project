package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.BuyProductDto;
import com.ecommerce.demo.entity.*;
import com.ecommerce.demo.exception.UserNotFoundException;
import com.ecommerce.demo.repository.*;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BuyProductService {

    @Autowired
    ProductInventoryRepository productInventoryRepository;

    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserPaymentRepository userPaymentRepository;

    @Autowired
    OrderDetailsRepository orderDetailsRepository;
    @Autowired
    OrderItemsRepository orderItemsRepository;


    public String buyProducts(int id, BuyProductDto buyProductDto) {

        Optional<Users> user = userRepository.findById(id);
        if (user.isPresent()) {

            OrderDetails orderDetails = new OrderDetails();

            List<OrderItems> orderItemsList = new ArrayList<>();

            UserPayment userPaymentRequestBody = buyProductDto.getUserPayment();
            Users users = userRepository.findByUserPaymentsIdAndId(userPaymentRequestBody.getId(), id);

            if (users != null) {
                for (UserPayment userPayment : users.getUserPayments())
                    if (userPayment.getId() == userPaymentRequestBody.getId()) {
                        orderDetails.setUserPayments(userPayment);
                        orderDetails.setUser(users);
                    }

            } else {
                throw new IllegalArgumentException("User Payment Is Not Exist");
            }

            List<CartItem> cartItem1 = cartItemRepository.findCartInUser(id);
            for (CartItem cartItem : cartItem1) {
                int cartItemId = cartItem.getId();
                for (CartItem cartItem2 : buyProductDto.getCartItemSet()) {
                    int cartItemId2 = cartItem2.getId();
                    if (cartItemId == cartItemId2) {
                        CartItem cartItem3 = cartItemRepository.findById(cartItemId).orElseThrow(() -> new IllegalArgumentException("CardId Not Found"));
                        ProductInventory productInventory = cartItem3.getProduct().getProductInventory();
                        int productQuantity = productInventory.getQuantity() - cartItem3.getQuantity();
                        if (productQuantity < 0) {
                            throw new IllegalArgumentException("Product Inventory Has No Sufficient Quantity");
                        }
                        productInventory.setQuantity(productQuantity);

                        Product product = cartItem3.getProduct();

                        OrderItems orderItems = new OrderItems();
                        orderItems.setProduct(product);
                        orderItemsList.add(orderItems);

                        productInventoryRepository.save(productInventory);
                        cartItemRepository.deleteById(cartItemId);
                        break;
                    }

                }
            }
            orderDetails.setOrderItems(orderItemsList);
            orderDetailsRepository.save(orderDetails);
        } else {
            throw new UserNotFoundException("User Not Found");
        }
        return "Buy Products Successfully";
    }

}

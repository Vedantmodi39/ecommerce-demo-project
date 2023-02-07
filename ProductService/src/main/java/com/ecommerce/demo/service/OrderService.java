package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.CartItemDto;
import com.ecommerce.demo.entity.CartItem;
import com.ecommerce.demo.entity.Product;
import com.ecommerce.demo.entity.Users;
import com.ecommerce.demo.repository.CartItemRepository;
import com.ecommerce.demo.repository.ProductRepository;
import com.ecommerce.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Set<CartItemDto> updateCart(Set<CartItemDto> cartItemDtos, int userId){
        Users user = userRepository.findById(userId).orElse(null);
        if(user == null)
            return null;

        List<CartItem> cartItemList = user.getCartItems();
        Set<Product> productSet = cartItemList.stream().map(CartItem::getProduct).collect(Collectors.toSet());

        List<CartItem> cartItemListNew = new ArrayList<>();

        for(CartItemDto cartItemDto : cartItemDtos){
            Product product =  productRepository.findBySku(cartItemDto.getProductSku()).orElse(null);
            if(product == null)
                continue;
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
                cartItem.setQuantity(cartItemDto.getQuantity());
            }
            cartItem.setProduct(product);
            cartItemListNew.add(cartItem);
        }
        user.setCartItems(cartItemListNew);
        userRepository.save(user);
        return cartItemDtos;
    }
}

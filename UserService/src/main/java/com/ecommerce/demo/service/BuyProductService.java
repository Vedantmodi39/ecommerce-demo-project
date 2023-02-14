package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.BuyProductDto;
import com.ecommerce.demo.entity.CartItem;
import com.ecommerce.demo.entity.ProductInventory;
import com.ecommerce.demo.exception.UserNotFoundException;
import com.ecommerce.demo.repository.CartItemRepository;
import com.ecommerce.demo.repository.ProductInventoryRepository;
import com.ecommerce.demo.repository.ProductRepository;
import com.ecommerce.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyProductService {

    @Autowired
    ProductInventoryRepository productInventoryRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    public String buyProducts(int id,BuyProductDto buyProductDto) {
        List<CartItem> cartItem1=cartItemRepository.findCartInUser(id);
        for(CartItem cartItem:cartItem1)
        {
            int cartItemId=cartItem.getId();
            for (CartItem cartItem2 : buyProductDto.getCartItemSet())
            {
                 int cartItemId2=cartItem2.getId();
                if (cartItemId==cartItemId2){
                    CartItem cartItem3 =cartItemRepository.findById(cartItemId).orElseThrow(() -> new IllegalArgumentException("CardId Not Found"));
                    ProductInventory productInventory = cartItem3.getProduct().getProductInventory();
                    int productQuantity = productInventory.getQuantity() - cartItem3.getQuantity();
                    if(productQuantity <0)
                    {
                        throw new IllegalArgumentException("Product Inventory Has No Sufficient Quantity");
                    }
                     productInventory.setQuantity(productQuantity);
                    productInventoryRepository.save(productInventory);
                    cartItemRepository.deleteById(cartItemId);
                    break;
                }
            }
        }
        return  "Buy Products Succesfully";
    }
}

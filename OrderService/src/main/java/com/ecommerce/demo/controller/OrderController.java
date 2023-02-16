package com.ecommerce.demo.controller;


import com.ecommerce.demo.dto.CartItemDto;
import com.ecommerce.demo.dto.GenericResponse;
import com.ecommerce.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @PostMapping("/updateCart/{userId}")
    public ResponseEntity<GenericResponse> updateCart(@RequestBody Set<CartItemDto> cartItemDtos, @PathVariable int userId)
    {
        GenericResponse genericResponse = new GenericResponse(true, "Product Added to cart Successfully", orderService.updateCart(cartItemDtos,userId), HttpStatus.OK.value());
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);

    }
}

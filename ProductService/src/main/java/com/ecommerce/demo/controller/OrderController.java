package com.ecommerce.demo.controller;


import com.ecommerce.demo.dto.CartItemDto;
import com.ecommerce.demo.dto.GenericResponse;
import com.ecommerce.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class OrderController {

    private final OrderService orderService;
    @PutMapping("/updateCart/{userId}")
    public ResponseEntity<GenericResponse> updateCart(@RequestBody Set<CartItemDto> cartItemDtos, @PathVariable int userId) throws InterruptedException, ExecutionException, TimeoutException {
        GenericResponse genericResponse = new GenericResponse(true, "Product Added to cart Successfully", orderService.updateCart(cartItemDtos,userId), HttpStatus.OK.value());
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }
    @GetMapping("/getCart/{userId}")
    public ResponseEntity<GenericResponse> updateCart(@PathVariable int userId) throws ExecutionException, InterruptedException {
        GenericResponse genericResponse = new GenericResponse(true, "Fetching Cart Items",orderService.getCart(userId), HttpStatus.OK.value());
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }
}

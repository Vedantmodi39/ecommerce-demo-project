package com.ecommerce.demo.controller;

import com.ecommerce.demo.dto.BuyNowProductDto;
import com.ecommerce.demo.dto.BuyProductDto;
import com.ecommerce.demo.dto.GenericResponse;
import com.ecommerce.demo.repository.CartItemRepository;
import com.ecommerce.demo.repository.ProductRepository;
import com.ecommerce.demo.service.BuyProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;


@RestController
@Slf4j
public class BuyProductsController {

@Autowired
BuyProductService buyProductService;

@PostMapping("/buyProducts/{id}")
public ResponseEntity<GenericResponse> buyProducts(@PathVariable int id, @RequestBody BuyProductDto buyProductDto) throws ExecutionException, InterruptedException {
    log.info("Buy Product for {} ...", id);
    GenericResponse genericResponse = new GenericResponse(true, "Buy Product Successfully",buyProductService.buyProducts(id,buyProductDto), HttpStatus.OK.value());
    return new ResponseEntity<>(genericResponse, HttpStatus.OK);
}
@GetMapping("/myOrders/{id}")
public ResponseEntity<GenericResponse> getMyOrders(@PathVariable int id)
{
    log.info("Get My Order for {} ...", id);
    GenericResponse genericResponse = new GenericResponse(true, "Fetched MyOrders Successfully",buyProductService.getMyOrders(id), HttpStatus.OK.value());
    return new ResponseEntity<>(genericResponse, HttpStatus.OK);
}

    @PostMapping("/buyNow/{userId}")
    public ResponseEntity<GenericResponse> buyNow(@PathVariable int userId, @RequestBody BuyNowProductDto buyNowProductDto){
    log.info("Buy Product Now For {} ...",userId);
    GenericResponse genericResponse = new GenericResponse(true,"Product Ordered Successfully",buyProductService.buyNowProduct(userId,buyNowProductDto),HttpStatus.OK.value());
    return new ResponseEntity<>(genericResponse,HttpStatus.OK);
    }

}

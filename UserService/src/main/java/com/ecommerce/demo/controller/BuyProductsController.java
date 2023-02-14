package com.ecommerce.demo.controller;

import com.ecommerce.demo.dto.BuyProductDto;
import com.ecommerce.demo.dto.GenericResponse;
import com.ecommerce.demo.repository.CartItemRepository;
import com.ecommerce.demo.repository.ProductRepository;
import com.ecommerce.demo.service.BuyProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@Slf4j
public class BuyProductsController {

@Autowired
BuyProductService buyProductService;

@PostMapping("/buyProducts/{id}")
public ResponseEntity<GenericResponse> buyProducts(@PathVariable int id, @RequestBody BuyProductDto buyProductDto)
{
    GenericResponse genericResponse = new GenericResponse(true, "Buy Product Successfully",buyProductService.buyProducts(id,buyProductDto), HttpStatus.OK.value());
    return new ResponseEntity<>(genericResponse, HttpStatus.OK);
}

}

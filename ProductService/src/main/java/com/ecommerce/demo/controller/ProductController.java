package com.ecommerce.demo.controller;

import com.ecommerce.demo.dto.GenericResponse;
import com.ecommerce.demo.dto.ProductDtoWithCategoryAndInventory;
import com.ecommerce.demo.entity.Product;
import com.ecommerce.demo.entity.ProductCategory;
import com.ecommerce.demo.exception.CategoryNotExistException;
import com.ecommerce.demo.exception.ProductAlreadyExistException;
import com.ecommerce.demo.repository.ProductCategoryRepository;
import com.ecommerce.demo.repository.ProductRepository;
import com.ecommerce.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
public class ProductController {


    @Autowired
    ProductService productService;

    @PostMapping("/addProduct")
    public ResponseEntity<GenericResponse> addProduct(@RequestBody ProductDtoWithCategoryAndInventory productDtoWithCategoryAndInventory)
    {
        GenericResponse genericResponse = new GenericResponse(true, "Product Added Successfully", productService.addProduct(productDtoWithCategoryAndInventory), HttpStatus.OK.value());
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);

    }

    @GetMapping("/getProduct/{productId}")
    public ResponseEntity<GenericResponse> getProduct(@PathVariable(name = "id") int  productId)
    {
        GenericResponse genericResponse=new GenericResponse(true,"Fetched Product Successfully",productService.getProduct(productId),HttpStatus.OK.value());
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }


}
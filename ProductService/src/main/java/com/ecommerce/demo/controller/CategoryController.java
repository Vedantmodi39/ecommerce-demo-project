package com.ecommerce.demo.controller;

import com.ecommerce.demo.dto.GenericResponse;
import com.ecommerce.demo.dto.ProductCategoryDto;
import com.ecommerce.demo.service.CategoryService;
import dto.CategoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/addCategory")
    public ResponseEntity<GenericResponse> addCategory(@RequestBody CategoryDto categoryDto){
        log.info("Creating Category {}...", categoryDto.getName());
        GenericResponse genericResponse = new GenericResponse(true,"Category Added Successfully",categoryService.addCategory(categoryDto), HttpStatus.OK.value());
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }
}

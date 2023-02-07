package com.ecommerce.demo.controller;

import com.ecommerce.demo.dto.GenericResponse;
import com.ecommerce.demo.dto.UserAddressPaymentDto;
import com.ecommerce.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/addUser")
    public ResponseEntity<GenericResponse> addUser(@RequestBody UserAddressPaymentDto userAddressPaymentDto)  {
        GenericResponse genericResponse = new GenericResponse(true ,"User Added Successfully." , userService.addUser(userAddressPaymentDto), HttpStatus.OK.value());
        return new ResponseEntity<>(genericResponse , HttpStatus.OK);
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<GenericResponse> getUser(@PathVariable int userId){
        GenericResponse genericResponse = new GenericResponse(true ,"User Fetched Successfully." , userService.getUser(userId), HttpStatus.OK.value());
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<GenericResponse> removeUser(@PathVariable int userId){
        GenericResponse genericResponse = new GenericResponse(true,"User Deleted Successfully" , userService.deleteUser(userId) , HttpStatus.OK.value());
        return  new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }



}

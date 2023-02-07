package com.ecommerce.demo.controller;

import com.ecommerce.demo.dto.GenericResponse;
import com.ecommerce.demo.dto.UserAddressPaymentDto;
import com.ecommerce.demo.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/addUser")
    public ResponseEntity<GenericResponse> addUser(@RequestBody UserAddressPaymentDto userAddressPaymentDto)  {
        log.info("Created New User with E-mail {}",userAddressPaymentDto.getUserDto().getEmail());
        GenericResponse genericResponse = new GenericResponse(true ,"User Added Successfully." , userService.addUser(userAddressPaymentDto), HttpStatus.OK.value());
        return new ResponseEntity<>(genericResponse , HttpStatus.OK);
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<GenericResponse> getUser(@PathVariable int userId){
        log.info("Getting User Info With User-Id{}",userId);
        GenericResponse genericResponse = new GenericResponse(true ,"User Fetched Successfully." , userService.getUser(userId), HttpStatus.OK.value());
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<GenericResponse> removeUser(@PathVariable int userId){
        log.info("Deleting User With User-Id {}",userId);
        GenericResponse genericResponse = new GenericResponse(true,"User Deleted Successfully" , userService.deleteUser(userId) , HttpStatus.OK.value());
        return  new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }



}

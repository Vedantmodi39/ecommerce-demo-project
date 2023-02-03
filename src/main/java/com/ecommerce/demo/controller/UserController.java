package com.ecommerce.demo.controller;

import com.ecommerce.demo.dto.GenericResponse;
import com.ecommerce.demo.entity.Users;
import com.ecommerce.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<GenericResponse> addUser(@RequestBody Users users)  {
        GenericResponse genericResponse = new GenericResponse(true ,"User Added Successfully." , userService.addUser(users), HttpStatus.OK.value());
        return new ResponseEntity<>(genericResponse , HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<GenericResponse> getUser(@PathVariable int userId){
        GenericResponse genericResponse = new GenericResponse(true ,"User Fetched Successfully." , userService.getUser(userId), HttpStatus.OK.value());
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<GenericResponse> removeUser(@PathVariable int userId){
        GenericResponse genericResponse = new GenericResponse(true,"User Deleted Successfully" , userService.deleteUser(userId) , HttpStatus.OK.value());
        return  new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }



}

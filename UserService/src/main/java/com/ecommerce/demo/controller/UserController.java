package com.ecommerce.demo.controller;

import com.ecommerce.demo.dto.EmptyJsonBody;
import com.ecommerce.demo.dto.GenericResponse;
import com.ecommerce.demo.dto.UserDto;
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
    public ResponseEntity<GenericResponse> addUser(@RequestBody UserDto userDto)  {
        log.info("Created New User with E-mail {}",userDto.getEmail());
        GenericResponse genericResponse = new GenericResponse(true ,"User Added Successfully." , userService.addUser(userDto), HttpStatus.OK.value());
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
        GenericResponse genericResponse = new GenericResponse(true,"User Deleted Successfully" , new EmptyJsonBody(), HttpStatus.OK.value());
        return  new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<GenericResponse> updateUser(@PathVariable int userId , @RequestBody UserDto userDto){
        log.info("Updating User with User-Id {}",userId);
        GenericResponse genericResponse = new GenericResponse(true,"User Updated Successfully",userService.updateUser(userDto,userId),HttpStatus.OK.value());
        return new ResponseEntity<>(genericResponse , HttpStatus.OK);
    }



}

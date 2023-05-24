package com.ecommerce.demo.controller;

import com.ecommerce.demo.dto.EmptyJsonBody;
import com.ecommerce.demo.dto.GenericResponse;
import com.ecommerce.demo.dto.UserDto;
import com.ecommerce.demo.entity.UserAddress;
import com.ecommerce.demo.entity.UserPayment;
import com.ecommerce.demo.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserServiceImpl userService;
    ObjectMapper objectMapper = new ObjectMapper();

    ObjectWriter objectWriter = objectMapper.writer();

    private UserDto userDto;


    @BeforeEach
    public void init(){
        userDto = new UserDto("new","password","new","user","12345","new@gmai.com",null,null,null);
        List<UserAddress> addressList = new ArrayList<>();
        addressList.add(new UserAddress(1,"xyz","rajkot","gujrat","india","360005","12345"));

        List<UserPayment> paymentList = new ArrayList<>();
        paymentList.add(new UserPayment(1,"credit card","visa","123", "2023-02-09 12:36:15.589569"));

        userDto.setUserAddressesList(addressList);
        userDto.setUserPaymentsList(paymentList);
    }

    @Test
    void getUser() throws Exception{

        GenericResponse genericResponse = new GenericResponse(true ,"User Fetched Successfully." , userDto, HttpStatus.OK.value());

        Mockito.when(userService.getUser(1)).thenReturn(userDto);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/user/getUser/"+1)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().string(objectMapper.writeValueAsString(genericResponse)))
                        .andExpect(jsonPath("$", notNullValue()));

    }
    @Test
    void addUser() throws Exception{

//        GenericResponse genericResponse = new GenericResponse(true ,"User Added Successfully." ,userDto, HttpStatus.OK.value());

        String content = objectWriter.writeValueAsString(userDto);

        Mockito.when(userService.addUser(userDto)).thenReturn(userDto);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/addUser")
                .content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateUser() throws Exception{
        GenericResponse genericResponse = new GenericResponse(true,"User Updated Successfully",userDto,HttpStatus.OK.value());

        String content = objectWriter.writeValueAsString(userDto);

        Mockito.when(userService.updateUser(userDto,1)).thenReturn(userDto);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/user/updateUser/1")
                .content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));

    }

    @Test
    void deleteUser() throws Exception{
        GenericResponse genericResponse = new GenericResponse(true,"User Deleted Successfully" , new EmptyJsonBody(), HttpStatus.OK.value());
        String content = objectWriter.writeValueAsString(1);
        Mockito.doNothing().when(userService).deleteUser(1);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/user/deleteUser/1")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}

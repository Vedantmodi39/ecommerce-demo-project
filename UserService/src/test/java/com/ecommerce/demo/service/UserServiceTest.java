package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.UserDto;
import com.ecommerce.demo.entity.Users;
import com.ecommerce.demo.mapstruct.MapStructMapper;
import com.ecommerce.demo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private MapStructMapper mapStructMapper;
    Users users = Users.builder()
            .username("testuser")
            .password("password")
            .email("test@gmail.com")
            .firstName("test")
            .lastName("user")
            .phoneNo("12345")
            .isDeleted(false)
            .createdAt(LocalDateTime.now())
            .modifiedAt(LocalDateTime.now())
            .cartItems(null)
            .userAddresses(null)
            .userPayments(null)
            .build();

    UserDto userdto = UserDto.builder()
            .username("testuser")
            .password("password")
            .email("test@gmail.com")
            .firstName("test")
            .lastName("user")
            .phoneNo("12345")
            .userPaymentsList(null)
            .userAddressesList(null)
            .cartItemsList(null)
            .build();

    @Test
    public void Userservice_addUser_ReturnsUserdto(){


        when(mapStructMapper.userDtoToUser(Mockito.any(UserDto.class))).thenReturn(users);
        when(userRepository.save(Mockito.any(Users.class))).thenReturn(users);

        Object savedUser = userService.addUser(userdto);

        Assertions.assertThat(savedUser).isNotNull();
    }

    @Test
    public void UserService_GetUser_ReturnsUserDto(){


        when(mapStructMapper.UserToUserDto(users)).thenReturn(userdto);
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(users));

        UserDto savedUser = userService.getUser(1);

        Assertions.assertThat(savedUser).isNotNull();
    }

    @Test
    public void UserService_updateUser_ReturnsUserDto(){

        UserDto updateUserdto = UserDto.builder()
                .username("updateduser")
                .password("password")
                .email("test@gmail.com")
                .firstName("updated")
                .lastName("user")
                .phoneNo("12345")
                .userPaymentsList(null)
                .userAddressesList(null)
                .cartItemsList(null)
                .build();

        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(users));
        when(mapStructMapper.updateUserfromDto(updateUserdto,users)).thenReturn(users);
        when(userRepository.save(Mockito.any(Users.class))).thenReturn(users);

        UserDto updatedUser = userService.updateUser(updateUserdto,1);

        System.out.println("Updated user :: "+updatedUser);

        Assertions.assertThat(updatedUser).isNotNull();

    }

    @Test
    public void UserService_deleteUser_ReturnsVoid(){

        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(users));
        when(userRepository.save(Mockito.any(Users.class))).thenReturn(users);

        assertAll(()-> userService.deleteUser(1));
    }
}

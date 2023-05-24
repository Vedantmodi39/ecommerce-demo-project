package com.ecommerce.demo.repository;

import com.ecommerce.demo.entity.Users;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
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

    @Test
    public void UserRepository_Save_ReturnsSavedUser(){

        Users savedUser = userRepository.save(users);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void UserRepository_FindbyId_ReturnsSavedUser(){

        Users savedUser = userRepository.save(users);

        Users responseUser = userRepository.findById(users.getId()).get();

        Assertions.assertThat(responseUser).isNotNull();
    }

    @Test
    public void UserRepository_FindbyEmail_ReturnsSavedUser(){


        Users savedUser = userRepository.save(users);

        Users responseUser = userRepository.findByEmail(users.getEmail()).get();

        Assertions.assertThat(responseUser).isNotNull();
        Assertions.assertThat(responseUser.getEmail()).isEqualTo(users.getEmail());
    }
}

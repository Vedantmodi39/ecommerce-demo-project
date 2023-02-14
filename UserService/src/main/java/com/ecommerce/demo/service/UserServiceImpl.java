package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.UserDto;
import com.ecommerce.demo.entity.Users;
import com.ecommerce.demo.exception.UserAlreadyExistException;
import com.ecommerce.demo.exception.UserNotFoundException;
import com.ecommerce.demo.mapstruct.MapStructMapper;
import com.ecommerce.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

    private final MapStructMapper mapStructMapper;

    public UserServiceImpl(UserRepository userRepository, MapStructMapper mapStructMapper) {
        this.userRepository = userRepository;
        this.mapStructMapper = mapStructMapper;
    }

    public Object addUser(UserDto userDto)  {
        Optional<Users> user = userRepository.findByEmail(userDto.getEmail());

        if(user.isPresent()){
            throw new UserAlreadyExistException(userDto.getEmail()+"");
        }else {

            Users users = mapStructMapper.userDtoToUser(userDto);
            users.setDeleted(false);
            users.setCreatedAt(LocalDateTime.now());
            users.setModifiedAt(LocalDateTime.now());

            userRepository.save(users);
            return userDto;
        }
    }

    public UserDto getUser(int userId){
        Optional<Users> savedUser = userRepository.findById(userId);
        if(savedUser.isPresent() && !savedUser.get().isDeleted()){
            UserDto userDto = mapStructMapper.UserToUserDto(savedUser.get());
            return userDto;
        }
        else {
            throw new UserNotFoundException("User Not Found !!");
        }
    }

    public void deleteUser(int userId){
        Optional<Users> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new UserNotFoundException("User Not Found !!");
        }else
            user.get().setDeleted(true);
            user.get().setModifiedAt(LocalDateTime.now());
        userRepository.save(user.get());

    }

    public UserDto updateUser(UserDto userDto , int id){
        Optional<Users> newUser = userRepository.findById(id);
        if(newUser.isPresent() && !newUser.get().isDeleted()){
            Users users = mapStructMapper.updateUserfromDto(userDto , newUser.get());
            users.setModifiedAt(LocalDateTime.now());
            userRepository.save(users);
            return userDto;
        }else {
            throw new UserNotFoundException("User Not found with ID :"+id);
        }
    }
}

package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService{
    //인증처리를 위한 UserDetailsService를 상속받아야 함.
    UserEntity createUser(UserDto userDto);
    Iterable<UserEntity> getUserByAll();
    UserEntity getUserByUserId(String userId);
    UserDto getUserDetailsByEmail(String userName);
}

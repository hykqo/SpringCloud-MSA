package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;

public interface UserService {
    UserEntity createUser(UserDto userDto);
}

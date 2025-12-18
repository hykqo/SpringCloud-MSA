package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/user-service")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @Value("${greeting.message}")
    private String message;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in User Service";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return message;
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody @Valid RequestUser user){
        UserEntity createdUser = userService.createUser(UserDto.of(user.getEmail(), user.getName(), user.getPwd()));
        return new ResponseEntity(ResponseUser.of(createdUser), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> userList = userService.getUserByAll();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseUser.from(userList));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUserById(@PathVariable String userId) {
        UserEntity user = userService.getUserByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseUser.of(user));
    }
}

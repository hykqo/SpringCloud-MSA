package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Value("${greeting.message}")
    private String message;

    @GetMapping("health_check")
    public String status() {
        return "It's Working in User Service";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return message;
    }

    @PostMapping("/users")
    public String createUser(@RequestBody @Valid RequestUser user){

        UserDto userDto = UserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .pwd(user.getPwd())
                .build();

        userService.createUser(userDto);
        return "Created User";
    }
}

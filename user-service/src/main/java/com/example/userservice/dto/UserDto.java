package com.example.userservice.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class UserDto {
    private String userId;
    private String email;
    private String name;
    private String pwd;

    private UserDto(String userId, String email, String name, String pwd) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.pwd = pwd;
    }

    public static UserDto of(String userId, String email, String name, String pwd){
        return new UserDto(userId, email, name, pwd);
    }
}

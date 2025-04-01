package com.example.userservice.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {
    private String email;
    private String name;
    private String pwd;

    private UserDto(String email, String name, String pwd) {
        this.email = email;
        this.name = name;
        this.pwd = pwd;
    }

    public static UserDto of(String email, String name, String pwd){
        return new UserDto(email, name, pwd);
    }
}

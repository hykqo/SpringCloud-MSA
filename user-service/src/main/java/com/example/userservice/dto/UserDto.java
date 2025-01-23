package com.example.userservice.dto;

import lombok.*;

@Builder
@Getter
@Setter
public class UserDto {
    private String email;
    private String name;
    private String pwd;
    private String userId;
    private String createdAt;
}

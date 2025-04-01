package com.example.userservice.vo;

import com.example.userservice.entity.UserEntity;
import lombok.Builder;

@Builder
public record ResponseUser(
        String email,
        String name,
        String userId
)
{
    public static ResponseUser of(UserEntity user){
        return ResponseUser.builder()
                .email(user.getEmail())
                .name(user.getName())
                .userId(user.getUserId())
                .build();
    }
}

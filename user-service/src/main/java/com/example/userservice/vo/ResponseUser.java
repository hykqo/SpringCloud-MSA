package com.example.userservice.vo;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record ResponseUser(
        String email,
        String name,
        String userId,
        List<ResponseOrder> orders
)
{
    public static ResponseUser of(UserEntity user){
        return ResponseUser.builder()
                .email(user.getEmail())
                .name(user.getName())
                .userId(user.getUserId())
                .orders(new ArrayList<>())
                .build();
    }

    public static List<ResponseUser> from(Iterable<UserEntity> userList){
        List<ResponseUser> from = new ArrayList<>();
        userList.forEach(user -> from.add(ResponseUser.of(user)));
        return from;
    }


    public static ResponseUser of(UserDto user){
        return ResponseUser.builder()
                .email(user.getEmail())
                .name(user.getName())
                .userId(user.getUserId())
                .orders(user.getOrders())
                .build();
    }
}

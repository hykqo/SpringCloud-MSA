package com.example.userservice.entity;

import com.example.userservice.dto.UserDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, unique = true)
    private String userId;
    @Column(nullable = false)
    private String encryptedPwd;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public static UserEntity CREATE(UserDto userDto){
        UserEntity userEntity = new UserEntity();
        userEntity.userId = UUID.randomUUID().toString();
        userEntity.email = userDto.getEmail();
        userEntity.name = userDto.getName();
        userEntity.encryptedPwd = "temporary" + userDto.getPwd();
        userEntity.createdAt = LocalDateTime.now();
        return userEntity;
    }
}

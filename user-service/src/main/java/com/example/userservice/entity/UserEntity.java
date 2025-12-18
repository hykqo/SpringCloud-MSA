package com.example.userservice.entity;

import com.example.userservice.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @JsonIgnore
    private LocalDateTime createdAt;

    public static UserEntity CREATE(UserDto userDto, BCryptPasswordEncoder bCryptPasswordEncoder) {
        String encode = bCryptPasswordEncoder.encode(userDto.getPwd());
        UserEntity userEntity = new UserEntity();
        userEntity.userId = UUID.randomUUID().toString();
        userEntity.email = userDto.getEmail();
        userEntity.name = userDto.getName();
        userEntity.encryptedPwd = encode;
        userEntity.createdAt = LocalDateTime.now();
        return userEntity;
    }
}

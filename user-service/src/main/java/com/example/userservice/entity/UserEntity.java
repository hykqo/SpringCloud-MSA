package com.example.userservice.entity;

import com.example.userservice.dto.UserDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Column(nullable = false, unique = true)
    private String encryptedPwd;


    public static UserEntity CREATE(UserDto userDto){
        UserEntity userEntity = new UserEntity();
        userEntity.email = userDto.getEmail();
        userEntity.name = userDto.getName();
        userEntity.userId = userDto.getUserId();
        userEntity.encryptedPwd = userDto.getEncryptedPwd();
        return userEntity;
    }


}

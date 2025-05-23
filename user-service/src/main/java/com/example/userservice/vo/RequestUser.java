package com.example.userservice.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Getter
@NoArgsConstructor
public class RequestUser {

    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email not be less than 2 characters")
    private String email;
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name not be less than 2 characters")
    private String name;
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password not be less than 8 characters")
    private String pwd;

}

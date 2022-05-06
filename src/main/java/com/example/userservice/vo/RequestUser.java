package com.example.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestUser {
    @NotNull(message = "Name can not be null")
    @Size(min = 2, message = "Name not be less than two characters")
    private String name;

    @Email
    @NotNull(message = "Email can not be null")
    @Size(min = 2, message = "Email not be less than two characters")
    private String email;

    @NotNull(message = "Password can not be null")
    @Size(min = 8, message = "Password must be equal or grater than 8 characters")
    private String pwd;
}

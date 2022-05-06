package com.example.userservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private String id;

    private String userId;

    private String name;

    private String email;

    private String pwd;

    private String encryptedPwd;

    private LocalDateTime createAt;
}

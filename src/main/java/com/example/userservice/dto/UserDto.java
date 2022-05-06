package com.example.userservice.dto;

import com.example.userservice.vo.ResponseOrder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDto {

    private String id;

    private String userId;

    private String name;

    private String email;

    private String pwd;

    private String encryptedPwd;

    private LocalDateTime createAt;

    private List<ResponseOrder> orders;

}

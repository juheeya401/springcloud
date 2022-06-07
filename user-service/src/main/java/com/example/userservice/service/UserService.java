package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);

    UserDto getUserById(String userId);

    Iterable<UserDto> getUserAll();

    UserDto getUserDetailsByEmail(String userName);
}

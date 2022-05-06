package com.example.userservice.service;

import com.example.userservice.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUserById(String userId);

    Iterable<UserDto> getUserAll();
}

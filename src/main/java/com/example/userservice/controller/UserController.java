package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class UserController {

    private final Environment env;
    private final Greeting greeting;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working! in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", token secret_key=" + env.getProperty("token.secret_key")
                + ", token expiration time=" + env.getProperty("token.expiration_time")
        );
    }

    @GetMapping("/welcome")
    public String welcome() {
        return env.getProperty("greeting.message");
    }

    @GetMapping("/welcome2")
    public String welcome2() {
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@Validated @RequestBody RequestUser requestUser) {
        UserDto requestDto = modelMapper.map(requestUser, UserDto.class);
        UserDto resultDto = userService.createUser(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(modelMapper.map(resultDto, ResponseUser.class));
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserDto> users = userService.getUserAll();

        List<ResponseUser> result = new ArrayList<>();
        users.forEach(e -> {
            result.add(modelMapper.map(e, ResponseUser.class));
        });
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        UserDto users = userService.getUserById(userId);
        ResponseUser result = modelMapper.map(users, ResponseUser.class);
        return ResponseEntity.ok(result);
    }
}

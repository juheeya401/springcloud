package com.example.myfirstservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/first-service")
public class FirstServiceController {

    private final Environment env;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to First Service!";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header) {
        log.info(header);
        return "Hello! It's First Service!";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        // 포트정보 조회하는 2가지 방법
        log.info("Server PORT = {}", request.getServerPort());
        return String.format("Check! First. Server PORT = %s"
                , env.getProperty("local.server.port"));
    }
}

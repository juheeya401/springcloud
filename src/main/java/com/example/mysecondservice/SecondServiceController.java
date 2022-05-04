package com.example.mysecondservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/second-service")
public class SecondServiceController {

    private final Environment env;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Second Service!";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("second-request") String header) {
        log.info(header);
        return "Hello! It's Second Service!";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        // 포트정보 조회하는 2가지 방법
        log.info("Server PORT = {}", request.getServerPort());
        return String.format("Check! Second. Server PORT = %s"
                , env.getProperty("local.server.port"));
    }
}

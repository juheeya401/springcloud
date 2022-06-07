package com.example.userservice.security;

import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final Environment env;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Authorization(권한) 설정
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/actuator/**").permitAll()  // actuator 관련정보는 무조건 통과
                .antMatchers("/**").hasIpAddress("192.168.1.100") // 특정 IP만 접근허용
                .and()
                .addFilter(getAuthenticationFilter());

        http.headers().frameOptions().disable(); // h2-console 페이지 프레임깨지는 오류를 방지할 수 있다.
    }

    /**
     * Security 인증을 위해 구현한 Filter
     */
    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(), userService, env); // 내가 만든 Filter
        return authenticationFilter;
    }

    /**
     * Authentication(인증) 설정
     *
     * 인증절차
     * 1. select pwd from users where email=?
     * 2. db_pwd(encrypted) == input_pwd(encrypted)
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)            // 인증절차 로직이 구현된 구현체 지정
                .passwordEncoder(bCryptPasswordEncoder);    // 사용자 입력한 pwd를 인코딩하기위한 알고리즘 지정
    }
}

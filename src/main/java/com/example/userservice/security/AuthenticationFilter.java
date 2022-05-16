package com.example.userservice.security;

import com.example.userservice.vo.RequestLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 요청정보를 보냈을 때 처리해주는 메서드
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(  // 사용자가 입력한 데이터를 스프링이 사용하는 토큰으로 변환해서 전달하면
                    creds.getEmail(),
                    creds.getPwd(),
                    new ArrayList<>()
            );
            return getAuthenticationManager().authenticate(token); // 인증처리해주는 매니저에게 넘기면 매니저가 인증처리해 줄것임
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        //super.successfulAuthentication(request, response, chain, authResult);

        // todo 로그인성공 후 어떤 처리할 것인지 or 어떤 데이터를 리턴해 줄것인지
    }
}

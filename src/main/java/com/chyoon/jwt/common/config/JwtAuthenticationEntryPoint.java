package com.chyoon.jwt.common.config;


import com.chyoon.jwt.common.ResponseBody;
import com.chyoon.jwt.common.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {// 인증된 사용자 요청 처리, 인증 실패 등 주로 권한 없는 사용자 처리
//    private final HandlerExceptionResolver resolver;

//    public JwtAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
//        this.resolver = resolver;
//    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        request.setAttribute("exceptionMessage", authException.getMessage());// response log 찍힐 예정
        response.setStatus(HttpStatus.OK.value());
        setResponse(response, ResultCode.UNAUTHORIZED);
    }

    private void setResponse(HttpServletResponse response, ResultCode responseCode) throws IOException {
        ResponseBody responseBody = ResponseBody.builder().code(responseCode).result(null).build();
        String jsonString = new ObjectMapper().writeValueAsString(responseBody);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);// "application/json;charset=UTF-8"
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(jsonString);
    }
}
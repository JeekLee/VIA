package com.via.support.security.handler;

import com.via.core.error.ExceptionCreator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import static com.via.support.security.exception.SecurityException.ACCESS_TOKEN_NOT_FOUND;

@Slf4j
public class AuthorizationFailureEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        throw ExceptionCreator.create(ACCESS_TOKEN_NOT_FOUND, "URI: " + request.getRequestURI());
    }
}

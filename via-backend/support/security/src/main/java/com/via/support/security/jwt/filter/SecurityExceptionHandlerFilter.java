package com.via.support.security.jwt.filter;

import com.via.core.error.exception.*;
import com.via.support.security.dto.ExceptionResponse;
import com.via.support.security.properties.JwtProperties;
import com.via.support.security.utils.CookieUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.OutputStream;

import static com.via.support.security.exception.SecurityException.*;

@Slf4j
@RequiredArgsConstructor
public class SecurityExceptionHandlerFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }
        catch (AbstractException e) {
            createdExceptionResponse(response, e);
        }
    }

    private void createdExceptionResponse(HttpServletResponse response, AbstractException e) {
        log.error("[" + e.getErrorCode() + "] : " + e.getErrorLog());

        if (e.getErrorCode().equals(ACCESS_TOKEN_INVALID.getErrorCode()) || e.getErrorCode().equals(ACCESS_TOKEN_EXPIRED.getErrorCode())) {
            response.addHeader(HttpHeaders.SET_COOKIE, CookieUtil.createEmptyCookie(jwtProperties.getAccessToken().getTokenKey()).toString());
        }

        if (e.getErrorCode().equals(REFRESH_TOKEN_INVALID.getErrorCode())
                || e.getErrorCode().equals(REFRESH_TOKEN_EXPIRED.getErrorCode())) {
            response.addHeader(HttpHeaders.SET_COOKIE, CookieUtil.createEmptyCookie(jwtProperties.getRefreshToken().getTokenKey()).toString());
        }

        if (e.getErrorCode().equals(TEMP_TOKEN_INVALID.getErrorCode()) || e.getErrorCode().equals(TEMP_TOKEN_EXPIRED.getErrorCode())) {
            response.addHeader(HttpHeaders.SET_COOKIE, CookieUtil.createEmptyCookie(jwtProperties.getTemporaryToken().getTokenKey()).toString());
        }


        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(getHttpStatus(e).value());

        try (OutputStream os = response.getOutputStream()) {
            objectMapper.writeValue(os, ExceptionResponse.builder()
                    .errorCode(e.getErrorCode())
                    .message(e.getMessage())
                    .build()
            );
            os.flush();
        } catch (IOException ioException) {
            throw new RuntimeException("Failed to create HttpExceptionResponse", ioException);
        }
    }

    private HttpStatus getHttpStatus(AbstractException e) {
        if (e instanceof BadRequestException) return HttpStatus.BAD_REQUEST;
        if (e instanceof ForbiddenException) return HttpStatus.FORBIDDEN;
        if (e instanceof NotFoundException) return HttpStatus.NOT_FOUND;
        if (e instanceof UnauthorizedException) return HttpStatus.UNAUTHORIZED;
        if (e instanceof ServiceUnavailableException) return HttpStatus.SERVICE_UNAVAILABLE;
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}

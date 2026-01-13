package com.via.support.security.exception;

import com.via.core.error.ExceptionInterface;
import com.via.core.error.exception.ForbiddenException;
import com.via.core.error.exception.UnauthorizedException;
import lombok.Getter;

@Getter
public enum SecurityException implements ExceptionInterface {
    PERMISSION_REQUIRED("SEC-001", "Access permission denied.", ForbiddenException.class),

    ACCESS_TOKEN_NOT_FOUND("SEC-901", "Login required. Please log in to continue.", UnauthorizedException.class),
    ACCESS_TOKEN_INVALID("SEC-902", "Login session has expired. Please log in again.", UnauthorizedException.class),
    ACCESS_TOKEN_EXPIRED("SEC-903", "Login session has expired. Please log in again.", UnauthorizedException.class),

    REFRESH_TOKEN_NOT_FOUND("SEC-904", "Login required. Please log in to continue.", UnauthorizedException.class),
    REFRESH_TOKEN_INVALID("SEC-905", "Login session has expired. Please log in again.", UnauthorizedException.class),
    REFRESH_TOKEN_EXPIRED("SEC-906", "Login session has expired. Please log in again.", UnauthorizedException.class),

    TEMP_TOKEN_NOT_FOUND("SEC-907", "Login required. Please log in to continue.", UnauthorizedException.class),
    TEMP_TOKEN_INVALID("SEC-908", "Session has expired. Please try again.", UnauthorizedException.class),
    TEMP_TOKEN_EXPIRED("SEC-909", "Session has expired. Please try again.", UnauthorizedException.class),
    ;

    private final String errorCode;
    private final String message;
    private final Class<?> aClass;

    SecurityException(String errorCode, String message, Class<?> aClass) {
        this.errorCode = errorCode;
        this.message = message;
        this.aClass = aClass;
    }
}

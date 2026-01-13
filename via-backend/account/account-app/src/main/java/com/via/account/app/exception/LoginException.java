package com.via.account.app.exception;

import com.via.core.error.ExceptionInterface;
import com.via.core.error.exception.NotFoundException;
import lombok.Getter;

@Getter
public enum LoginException implements ExceptionInterface {
    SESSION_NOT_FOUND("LOGIN-001", "Login session does not exist. Please log in again.", NotFoundException.class),
    OAUTH2_CONTEXT_NOT_FOUND("LOGIN-002", "Login session has expired. Please log in again.", NotFoundException.class),
    MEMBER_NOT_FOUND("LOGIN-001", "User does not exist.", NotFoundException.class),
    ;

    private final String errorCode;
    private final String message;
    private final Class<?> aClass;

    LoginException(String errorCode, String message, Class<?> aClass) {
        this.errorCode = errorCode;
        this.message = message;
        this.aClass = aClass;
    }
}

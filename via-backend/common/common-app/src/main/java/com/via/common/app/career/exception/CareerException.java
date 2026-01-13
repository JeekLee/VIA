package com.via.common.app.career.exception;

import com.via.core.error.ExceptionInterface;
import com.via.core.error.exception.NotFoundException;
import lombok.Getter;

@Getter
public enum CareerException implements ExceptionInterface {
    CAREER_NOT_FOUND("CAREER-001", "Career information does not exist.", NotFoundException.class),
    ;

    private final String errorCode;
    private final String message;
    private final Class<?> aClass;

    CareerException(String errorCode, String message, Class<?> aClass) {
        this.errorCode = errorCode;
        this.message = message;
        this.aClass = aClass;
    }
}

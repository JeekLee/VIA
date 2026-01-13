package com.via.account.app.exception;

import com.via.core.error.ExceptionInterface;
import com.via.core.error.exception.NotFoundException;
import lombok.Getter;

@Getter
public enum MemberException implements ExceptionInterface {
    MEMBER_NOT_FOUND("MEMBER-001", "Member does not exist. Please log in again.", NotFoundException.class),
    ;

    private final String errorCode;
    private final String message;
    private final Class<?> aClass;

    MemberException(String errorCode, String message, Class<?> aClass) {
        this.errorCode = errorCode;
        this.message = message;
        this.aClass = aClass;
    }
}

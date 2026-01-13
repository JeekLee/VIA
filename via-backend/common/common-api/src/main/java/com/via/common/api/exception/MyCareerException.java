package com.via.common.api.exception;

import com.via.core.error.ExceptionInterface;
import com.via.core.error.exception.BadRequestException;
import lombok.Getter;

@Getter
public enum MyCareerException implements ExceptionInterface {
    FAILED_TO_READ_IMAGE("MCR-001", "Failed to read image file. Please try again.", BadRequestException.class),
    ;

    private final String errorCode;
    private final String message;
    private final Class<?> aClass;

    MyCareerException(String errorCode, String message, Class<?> aClass) {
        this.errorCode = errorCode;
        this.message = message;
        this.aClass = aClass;
    }
}

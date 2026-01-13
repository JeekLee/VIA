package com.via.content.infra.course.exception;

import com.via.core.error.ExceptionInterface;
import com.via.core.error.exception.InternalServerErrorException;
import lombok.Getter;

@Getter
public enum CourseSearchException implements ExceptionInterface {
    QUERY_PROCESSING_FAILED("SCR-001", "Failed to generate search query.", InternalServerErrorException.class),
    ;

    private final String errorCode;
    private final String message;
    private final Class<?> aClass;

    CourseSearchException(String errorCode, String message, Class<?> aClass) {
        this.errorCode = errorCode;
        this.message = message;
        this.aClass = aClass;
    }
}

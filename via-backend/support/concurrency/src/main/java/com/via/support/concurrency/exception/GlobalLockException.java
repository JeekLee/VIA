package com.via.support.concurrency.exception;

import com.via.core.error.ExceptionInterface;
import com.via.core.error.exception.InternalServerErrorException;
import com.via.core.error.exception.ServiceUnavailableException;
import lombok.Getter;

@Getter
public enum GlobalLockException implements ExceptionInterface {
    LOCK_UNAVAILABLE("GLL-001", "Global lock is not available.", ServiceUnavailableException.class),
    LOCK_REQUEST_FAILED("GLL-002", "Global lock request failed.", InternalServerErrorException.class);

    private final String errorCode;
    private final String message;
    private final Class<?> aClass;

    GlobalLockException(String errorCode, String message, Class<?> aClass) {
        this.errorCode = errorCode;
        this.message = message;
        this.aClass = aClass;
    }
}

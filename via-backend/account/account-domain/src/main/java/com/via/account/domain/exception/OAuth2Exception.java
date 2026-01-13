package com.via.account.domain.exception;

import com.via.core.error.ExceptionInterface;
import com.via.core.error.exception.InternalServerErrorException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OAuth2Exception implements ExceptionInterface {
    FAILED_TO_CREATE_CODE_CHALLENGE("OAUTH-001", "Unable to proceed with login. Please try again.", InternalServerErrorException.class),
    ;

    private final String errorCode;
    private final String message;
    private final Class<?> aClass;
}

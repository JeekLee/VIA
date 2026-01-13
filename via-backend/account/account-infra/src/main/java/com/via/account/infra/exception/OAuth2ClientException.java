package com.via.account.infra.exception;

import com.via.core.error.ExceptionInterface;
import com.via.core.error.exception.BadRequestException;
import com.via.core.error.exception.InternalServerErrorException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OAuth2ClientException implements ExceptionInterface {
    PROVIDER_NOT_SUPPORTED("OAC-001", "This social login provider is not supported.", BadRequestException.class),
    FAILED_TO_GET_ACCESS_TOKEN("OAC-002", "Login failed. Please try again.", InternalServerErrorException.class),
    FAILED_TO_GET_USER_PROFILE("OAC-003", "Failed to retrieve user information. Please try again.", InternalServerErrorException.class),
    ;

    private final String errorCode;
    private final String message;
    private final Class<?> aClass;
}

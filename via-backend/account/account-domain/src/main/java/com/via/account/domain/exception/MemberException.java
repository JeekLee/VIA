package com.via.account.domain.exception;

import com.via.core.error.ExceptionInterface;
import com.via.core.error.exception.BadRequestException;
import com.via.core.error.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberException implements ExceptionInterface {
    MEMBER_NOT_FOUND("MEMBER-001", "Member information not found. Please check again.", NotFoundException.class),
    NICKNAME_ALREADY_EXISTS("MEMBER-002", "This nickname is already in use.", BadRequestException.class),
    ;

    private final String errorCode;
    private final String message;
    private final Class<?> aClass;
}

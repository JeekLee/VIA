package com.via.account.infra.exception;

import com.via.core.error.ExceptionInterface;
import com.via.core.error.exception.InternalServerErrorException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberImageStorageException implements ExceptionInterface {
    IMAGE_UPLOAD_FAILED("MIS-001", "Failed to upload member image. Please try again.", InternalServerErrorException.class),
    ;

    private final String errorCode;
    private final String message;
    private final Class<?> aClass;
}

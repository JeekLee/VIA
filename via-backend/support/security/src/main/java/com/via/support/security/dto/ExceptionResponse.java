package com.via.support.security.dto;

import com.via.core.error.exception.AbstractException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Builder
public class ExceptionResponse {
    private final String errorCode;
    private final String message;
    private final String timeStamp = LocalDateTime.now(ZoneId.of("Asia/Seoul")).toString();

    public static ResponseEntity<ExceptionResponse> toResponseEntity(AbstractException e, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(
                        ExceptionResponse.builder()
                                .errorCode(e.getErrorCode())
                                .message(e.getMessage())
                                .build()
                );
    }

    public static ResponseEntity<ExceptionResponse> toResponseEntity(HttpStatus httpStatus, String errorCode, String message) {
        return ResponseEntity
                .status(httpStatus)
                .body(
                        ExceptionResponse.builder()
                                .errorCode(errorCode)
                                .message(message)
                                .build()
                );
    }
}

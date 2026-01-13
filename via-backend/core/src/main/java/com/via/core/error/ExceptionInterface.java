package com.via.core.error;

public interface ExceptionInterface {
    String getErrorCode();
    String getMessage();
    Class<?> getAClass();
}

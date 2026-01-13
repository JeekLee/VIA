package com.via.content.crawler.exception;

import com.via.core.error.ExceptionInterface;
import com.via.core.error.exception.InternalServerErrorException;
import com.via.core.error.exception.NotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InflearnCrawlerException implements ExceptionInterface {
    COURSE_LIST_NOT_FOUND("CRINF-001", "Course list not found", NotFoundException.class),
    ARTICLE_NOT_FOUND("CRINF-002", "Article element not found", NotFoundException.class),
    META_DIV_NOT_FOUND("CRINF-003", "Metadata div not found.", NotFoundException.class),
    URL_NOT_FOUND("CRINF-004", "Course url not found.", NotFoundException.class),
    FAILED_TO_PARSE_DIFFICULTY("CRINF-005", "Failed to parse level to difficulty.", InternalServerErrorException.class),
    ;

    private final String errorCode;
    private final String message;
    private final Class<?> aClass;
}

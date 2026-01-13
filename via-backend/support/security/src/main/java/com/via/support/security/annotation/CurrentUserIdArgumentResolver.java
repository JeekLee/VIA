package com.via.support.security.annotation;

import com.via.core.error.ExceptionCreator;
import com.via.support.security.dto.UserPrincipal;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.via.support.security.exception.SecurityException.AUTH_NOT_FOUND;
import static com.via.support.security.exception.SecurityException.INVALID_PRINCIPAL;

@Component
public class CurrentUserIdArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUserId.class)
                && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) throw ExceptionCreator.create(AUTH_NOT_FOUND);
        if (!(authentication.getPrincipal() instanceof UserPrincipal)) throw ExceptionCreator.create(INVALID_PRINCIPAL);

        return ((UserPrincipal) authentication.getPrincipal()).userId();
    }
}

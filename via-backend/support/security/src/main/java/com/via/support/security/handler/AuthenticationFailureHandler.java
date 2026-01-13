package com.via.support.security.handler;

import com.via.core.error.ExceptionCreator;
import com.via.support.security.dto.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import static com.via.support.security.exception.SecurityException.PERMISSION_REQUIRED;

@Slf4j
public class AuthenticationFailureHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException
    ) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserPrincipal principal = authentication.getPrincipal() instanceof UserPrincipal ? (UserPrincipal) authentication.getPrincipal() : null;
        String nickname = principal != null ? principal.nickname() : "unknown";
        throw ExceptionCreator.create(
                PERMISSION_REQUIRED,
                "Member [" + nickname + "] attempted to access protected URL " + request.getRequestURI()
        );
    }
}

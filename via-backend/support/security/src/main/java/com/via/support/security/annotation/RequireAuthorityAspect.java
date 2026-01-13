package com.via.support.security.annotation;

import com.via.support.security.enums.Authority;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class RequireAuthorityAspect {

    @Around("@annotation(requireAuthority)")
    public Object checkRole(ProceedingJoinPoint joinPoint, RequireAuthority requireAuthority) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) throw new AccessDeniedException("Not authenticated");

        List<String> currentUserRoles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        boolean hasAccess = Arrays.stream(requireAuthority.value())
                .map(Authority::getRole)
                .anyMatch(currentUserRoles::contains);

        if (!hasAccess) throw new AccessDeniedException("Not authorized");

        return joinPoint.proceed();
    }
}

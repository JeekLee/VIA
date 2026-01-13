package com.via.support.security.jwt.filter;

import com.via.support.security.dto.UserPrincipal;
import com.via.support.security.jwt.JwtResolver;
import com.via.support.security.properties.JwtProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProperties jwtProperties;
    private final JwtResolver jwtResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = Optional.ofNullable(request.getCookies())
                .stream()
                .flatMap(Arrays::stream)
                .filter(cookie -> cookie.getName().equals(jwtProperties.getAccessToken().getTokenKey()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        if (StringUtils.hasText(accessToken)) {
            UserPrincipal principal = jwtResolver.getMemberPrincipalFromAccessToken(accessToken);

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);

            filterChain.doFilter(request, response);
            return;
        }

        String temporaryToken = Optional.ofNullable(request.getCookies())
                .stream()
                .flatMap(Arrays::stream)
                .filter(cookie -> cookie.getName().equals(jwtProperties.getTemporaryToken().getTokenKey()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        if (StringUtils.hasText(temporaryToken)) {
            UserPrincipal principal = jwtResolver.getMemberPrincipalFromTemporaryToken(temporaryToken);

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);

            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }
}

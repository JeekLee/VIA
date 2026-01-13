package com.via.support.security.jwt;

import com.via.core.error.ExceptionCreator;
import com.via.support.security.dto.UserPrincipal;
import com.via.support.security.enums.Authority;
import com.via.support.security.properties.JwtProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.List;

import static com.via.support.security.exception.SecurityException.*;


@Component
@RequiredArgsConstructor
public class JwtResolver {
    private final JwtProperties jwtProperties;
    private Key accessTokenKey;
    private Key temporaryTokenKey;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        byte[] accessTokenBytes = Base64.getDecoder().decode(jwtProperties.getAccessToken().getSecretKey());
        accessTokenKey = Keys.hmacShaKeyFor(accessTokenBytes);

        byte[] temporaryTokenBytes= Base64.getDecoder().decode(jwtProperties.getTemporaryToken().getSecretKey());
        temporaryTokenKey = Keys.hmacShaKeyFor(temporaryTokenBytes);
    }

    public UserPrincipal getMemberPrincipalFromAccessToken(String token) {
        try {
            if (token == null) throw ExceptionCreator.create(ACCESS_TOKEN_NOT_FOUND);

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(accessTokenKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            List<String> rolesAsString = objectMapper.convertValue(
                    claims.get("authorities", List.class),
                    new TypeReference<>() {}
            );

            List<Authority> authorities = Authority.fromRoles(rolesAsString);

            return UserPrincipal.create(
                    claims.get("user_id", Long.class),
                    claims.get("nickname", String.class),
                    authorities
            );
        } catch (SecurityException | UnsupportedJwtException | SignatureException | MalformedJwtException | DecodingException e) {
            throw ExceptionCreator.create(ACCESS_TOKEN_INVALID, "AccessToken: " + token);
        } catch (ExpiredJwtException e) {
            throw ExceptionCreator.create(ACCESS_TOKEN_EXPIRED, "AccessToken: " + token);
        }
    }

    public UserPrincipal getMemberPrincipalFromTemporaryToken(String token) {
        try {
            if (token == null) throw ExceptionCreator.create(TEMP_TOKEN_NOT_FOUND);

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(temporaryTokenKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return UserPrincipal.create(
                    claims.get("user_id", Long.class),
                    null,
                    List.of(Authority.TEMP)
            );
        } catch (SecurityException | UnsupportedJwtException | SignatureException | MalformedJwtException | DecodingException e) {
            throw ExceptionCreator.create(TEMP_TOKEN_INVALID, "TemporaryToken: " + token);
        } catch (ExpiredJwtException e) {
            throw ExceptionCreator.create(TEMP_TOKEN_EXPIRED, "TemporaryToken: " + token);
        }
    }
}

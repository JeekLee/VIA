package com.via.support.security.jwt;

import com.via.support.security.dto.AuthToken;
import com.via.support.security.enums.Authority;
import com.via.support.security.properties.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.via.support.security.enums.AuthTokenType.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {
    private final JwtProperties jwtProperties;
    private Key accessTokenKey;
    private Key refreshTokenKey;
    private Key termporaryTokenKey;

    @PostConstruct
    public void init() {
        byte[] accessTokenBytes = Base64.getDecoder().decode(jwtProperties.getAccessToken().getSecretKey());
        accessTokenKey = Keys.hmacShaKeyFor(accessTokenBytes);

        byte[] refreshTokenBytes = Base64.getDecoder().decode(jwtProperties.getRefreshToken().getSecretKey());
        refreshTokenKey = Keys.hmacShaKeyFor(refreshTokenBytes);

        byte[] temporaryTokenBytes = Base64.getDecoder().decode(jwtProperties.getTemporaryToken().getSecretKey());
        termporaryTokenKey = Keys.hmacShaKeyFor(temporaryTokenBytes);
    }

    public AuthToken generateAccessToken(Long userId, String nickname, List<Authority> authorities) {
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        LocalDateTime now = LocalDateTime.now(zoneId);
        LocalDateTime expiresAt = now.plusSeconds(jwtProperties.getAccessToken().getExpiresIn());

        Date expiresAtInDate = Date.from(expiresAt.atZone(zoneId).toInstant());

        String token = Jwts.builder()
                .claim("user_id", userId)
                .claim("nickname", nickname)
                .claim("authorities", authorities.stream().map(Authority::getRole).collect(Collectors.toList()))
                .setExpiration(expiresAtInDate)
                .signWith(accessTokenKey, SignatureAlgorithm.HS256)
                .compact();
        log.debug("AccessToken Generated for user {}: {}", userId, token);

        return new AuthToken(token, ACCESS, expiresAt);
    }

    public AuthToken generateRefreshToken(Long userId) {
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        LocalDateTime now = LocalDateTime.now(zoneId);
        LocalDateTime expiresAt = now.plusSeconds(jwtProperties.getRefreshToken().getExpiresIn());

        Date expiresAtInDate = Date.from(expiresAt.atZone(zoneId).toInstant());

        String token = Jwts.builder()
                .claim("user_id", userId)
                .setExpiration(expiresAtInDate)
                .signWith(refreshTokenKey, SignatureAlgorithm.HS256)
                .compact();
        log.debug("RefreshToken Generated for user {}: {}", userId, token);

        return new AuthToken(token, REFRESH, expiresAt);
    }

    public AuthToken generateTemporaryToken(Long userId) {
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        LocalDateTime now = LocalDateTime.now(zoneId);
        LocalDateTime expiresAt = now.plusSeconds(jwtProperties.getTemporaryToken().getExpiresIn());

        Date expiresAtInDate = Date.from(expiresAt.atZone(zoneId).toInstant());

        String token = Jwts.builder()
                .claim("user_id", userId)
                .setExpiration(expiresAtInDate)
                .signWith(termporaryTokenKey, SignatureAlgorithm.HS256)
                .compact();
        log.debug("TemporaryToken Generated for user {}: {}", userId, token);

        return new AuthToken(token, TEMPORARY, expiresAt);
    }
}

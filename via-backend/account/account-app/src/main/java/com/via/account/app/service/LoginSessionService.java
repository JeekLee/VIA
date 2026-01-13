package com.via.account.app.service;

import com.via.account.app.repository.RefreshTokenRepository;
import com.via.account.domain.model.RefreshToken;
import com.via.account.domain.model.id.MemberId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginSessionService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void replace(MemberId memberId, String oldToken, String newToken, LocalDateTime expiresAt) {
        refreshTokenRepository.deleteByToken(oldToken);
        refreshTokenRepository.save(RefreshToken.create(memberId, newToken, expiresAt));
    }

    @Transactional
    public void create(MemberId memberId, String token, LocalDateTime expiresAt) {
        refreshTokenRepository.save(RefreshToken.create(memberId, token, expiresAt));
    }
}

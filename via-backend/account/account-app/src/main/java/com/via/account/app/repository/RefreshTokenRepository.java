package com.via.account.app.repository;


import com.via.account.domain.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
    void deleteByToken(String token);
    Optional<RefreshToken> findByToken(String token);
}

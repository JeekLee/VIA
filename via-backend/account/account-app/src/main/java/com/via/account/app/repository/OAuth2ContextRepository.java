package com.via.account.app.repository;


import com.via.account.domain.model.OAuth2Context;

import java.util.Optional;

public interface OAuth2ContextRepository {
    OAuth2Context save(OAuth2Context state);
    Optional<OAuth2Context> findByState(String state);
}

package com.via.account.app.repository;


import com.via.account.domain.enums.OAuth2Provider;
import com.via.account.domain.model.OAuth2Account;

import java.util.Optional;

public interface OAuth2AccountRepository {
    Optional<OAuth2Account> findByOAuth2ProviderAndEmail(OAuth2Provider provider, String email);
    OAuth2Account save(OAuth2Account oAuth2Account);
}

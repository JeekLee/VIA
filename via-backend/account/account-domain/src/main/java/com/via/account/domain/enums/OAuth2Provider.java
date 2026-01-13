package com.via.account.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuth2Provider {
    GOOGLE("google"),
    KAKAO("kakao"),
    FACEBOOK("facebook"),
    APPLE("apple"),
    ;

    private final String value;
}

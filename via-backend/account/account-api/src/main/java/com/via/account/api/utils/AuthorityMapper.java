package com.via.account.api.utils;

import com.via.account.domain.enums.MemberAuthority;
import com.via.support.security.enums.Authority;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorityMapper {

    public static Authority toAuthority(MemberAuthority memberAuthority) {
        return switch (memberAuthority) {
            case USER -> Authority.USER;
            case EXPERT -> Authority.EXPERT;
            case MANAGER -> Authority.MANAGER;
        };
    }

    public static List<Authority> toAuthorities(List<MemberAuthority> memberAuthorities) {
        if (memberAuthorities == null || memberAuthorities.isEmpty()) {
            return List.of();
        }
        return memberAuthorities.stream()
                .map(AuthorityMapper::toAuthority)
                .collect(Collectors.toList());
    }

}

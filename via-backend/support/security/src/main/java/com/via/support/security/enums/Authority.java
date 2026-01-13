package com.via.support.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Authority {
    USER("ROLE_USER"),
    EXPERT("ROLE_EXPERT"),
    MANAGER("ROLE_MANAGER"),
    TEMP("ROLE_TEMP"),
    ;

    private final String role;

    public static Authority fromRole(String role) {
        return Arrays.stream(values())
                .filter(authority -> authority.role.equals(role))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown role: " + role));
    }

    public static List<Authority> fromRoles(List<String> roles) {
        if (roles == null || roles.isEmpty()) return List.of();
        return roles.stream()
                .map(Authority::fromRole)
                .collect(Collectors.toList());
    }
}

package com.via.support.security.dto;

import com.via.support.security.enums.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

public record UserPrincipal (
        Long userId,
        String nickname,
        Collection<GrantedAuthority> authorities
) implements UserDetails, Principal {
    public static UserPrincipal create(Long userId, String nickname, List<Authority> authorities) {
        return new UserPrincipal(
                userId,
                nickname,
                authorities.stream()
                        .map(authority -> new SimpleGrantedAuthority(authority.getRole()))
                        .map(authority -> (GrantedAuthority) authority)
                        .toList()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getName() {
        return this.userId.toString();
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }
}

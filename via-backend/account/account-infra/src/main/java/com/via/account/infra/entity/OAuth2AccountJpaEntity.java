package com.via.account.infra.entity;

import com.via.account.domain.enums.OAuth2Provider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(
        name = "oauth2_account",
        catalog = "account",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "oauth2_provider"})}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2AccountJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "oauth2_provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuth2Provider oauth2Provider;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "initial_account", nullable = false)
    private Boolean initialAccount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public OAuth2AccountJpaEntity(Long id, Long memberId, OAuth2Provider oauth2Provider, String email, Boolean initialAccount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.memberId = memberId;
        this.oauth2Provider = oauth2Provider;
        this.email = email;
        this.initialAccount = initialAccount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

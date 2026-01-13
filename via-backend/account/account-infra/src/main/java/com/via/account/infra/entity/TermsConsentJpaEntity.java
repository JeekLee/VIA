package com.via.account.infra.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "terms_consent", catalog = "account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermsConsentJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "terms_id")
    private Long termsId;

    @Column(name = "withdrawn")
    public Boolean withdrawn;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public TermsConsentJpaEntity(Long id, Long memberId, Long termsId, Boolean withdrawn, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.memberId = memberId;
        this.termsId = termsId;
        this.withdrawn = withdrawn;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

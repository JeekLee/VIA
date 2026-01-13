package com.via.account.infra.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "terms", catalog = "account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermsJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "major_version", nullable = false)
    private Integer majorVersion;

    @Column(name = "minor_version", nullable = false)
    private Integer minorVersion;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "effective_at", nullable = false)
    private LocalDateTime effectiveAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "terms_category_id")
    private Long termsCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "terms_category_id", insertable = false, updatable = false)
    private TermsCategoryJpaEntity termsCategoryJpaEntity;

    @Builder
    public TermsJpaEntity(Long id, Integer majorVersion, Integer minorVersion, String title, String content, LocalDateTime effectiveAt, Long termsCategoryId, TermsCategoryJpaEntity termsCategoryJpaEntity) {
        this.id = id;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.title = title;
        this.content = content;
        this.effectiveAt = effectiveAt;
        this.termsCategoryId = termsCategoryId;
        this.termsCategoryJpaEntity = termsCategoryJpaEntity;
    }
}

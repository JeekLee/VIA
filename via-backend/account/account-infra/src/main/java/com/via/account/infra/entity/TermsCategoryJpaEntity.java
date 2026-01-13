package com.via.account.infra.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "terms_category", catalog = "account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermsCategoryJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "required", nullable = false)
    private boolean required;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "priority", nullable = false)
    private Integer priority;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public TermsCategoryJpaEntity(Long id, boolean required, String name, Integer priority, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.required = required;
        this.name = name;
        this.priority = priority;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

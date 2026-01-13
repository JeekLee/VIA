package com.via.common.infra.onboarding.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "onboarding" , catalog = "account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnboardingJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "activated")
    private Boolean activated;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public OnboardingJpaEntity(Long id, Integer priority, String title, String content, String imagePath, Boolean activated) {
        this.id = id;
        this.priority = priority;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.activated = activated;
    }
}

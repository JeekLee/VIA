package com.via.content.infra.course.database.entity;

import com.via.content.domain.course.enums.CourseDifficulty;
import com.via.content.domain.course.enums.CoursePlatform;
import com.via.content.infra.skill.database.entity.SkillJpaEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "course" , catalog = "content")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url", nullable = false, unique = true)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform", nullable = false)
    private CoursePlatform platform;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "instructor", nullable = false)
    private String instructor;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false)
    private CourseDifficulty difficulty;

    @Column(name = "rating", nullable = false)
    private Float rating;

    @ManyToMany
    @JoinTable(
            name = "course_skill",
            catalog = "content",
            joinColumns = @JoinColumn(name = "course_id", insertable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "skill_id", insertable = false, updatable = false)
    )
    private List<SkillJpaEntity> skills = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public CourseJpaEntity(Long id, String url, CoursePlatform platform,
                           String title, String instructor, String description,
                           CourseDifficulty difficulty, Float rating,
                           LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.url = url;
        this.platform = platform;
        this.title = title;
        this.instructor = instructor;
        this.description = description;
        this.difficulty = difficulty;
        this.rating = rating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

package com.via.content.infra.skill.database.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "skill" , catalog = "content")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SkillJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "aliases")
    private List<String> aliases;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public SkillJpaEntity(Long id, String name, List<String> aliases, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.aliases = aliases;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

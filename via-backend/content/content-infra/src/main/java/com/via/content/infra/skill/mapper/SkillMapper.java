package com.via.content.infra.skill.mapper;

import com.via.content.domain.course.model.Skill;
import com.via.content.domain.course.model.id.SkillId;
import com.via.content.infra.skill.database.entity.SkillJpaEntity;
import com.via.content.infra.skill.search.entity.AliasEmbedding;
import com.via.content.infra.skill.search.entity.SkillDocument;
import com.via.content.infra.skill.search.utils.SkillEmbeddingProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SkillMapper {
    private final SkillEmbeddingProvider embeddingProvider;

    public SkillJpaEntity toEntity(Skill skill) {
        return SkillJpaEntity.builder()
                .id(skill.id() != null ? skill.id().id() : null)
                .name(skill.name())
                .aliases(skill.aliases())
                .createdAt(skill.createdAt())
                .updatedAt(skill.updatedAt())
                .build();
    }

    public SkillDocument toDocument(Skill skill) {
        return SkillDocument.builder()
                .id(skill.id().id())
                .name(skill.name())
                .aliasEmbeddings(embeddingProvider.createAliasEmbedding(skill.aliases()))
                .createdAt(skill.createdAt())
                .updatedAt(skill.updatedAt())
                .build();
    }

    public SkillDocument toDocument(SkillJpaEntity skillJpaEntity) {
        return SkillDocument.builder()
                .id(skillJpaEntity.getId())
                .name(skillJpaEntity.getName())
                .aliasEmbeddings(embeddingProvider.createAliasEmbedding(skillJpaEntity.getAliases()))
                .createdAt(skillJpaEntity.getCreatedAt())
                .updatedAt(skillJpaEntity.getUpdatedAt())
                .build();
    }

    public Skill toDomain(SkillJpaEntity skillJpaEntity) {
        return Skill.builder()
                .id(new SkillId(skillJpaEntity.getId()))
                .name(skillJpaEntity.getName())
                .aliases(skillJpaEntity.getAliases())
                .createdAt(skillJpaEntity.getCreatedAt())
                .updatedAt(skillJpaEntity.getUpdatedAt())
                .build();
    }

    public Skill toDomain(SkillDocument skillDocument) {
        return Skill.builder()
                .id(new SkillId(skillDocument.getId()))
                .name(skillDocument.getName())
                .aliases(skillDocument.getAliasEmbeddings().stream()
                        .map(AliasEmbedding::alias)
                        .toList())
                .createdAt(skillDocument.getCreatedAt())
                .updatedAt(skillDocument.getUpdatedAt())
                .build();
    }
}

package com.via.content.infra.skill.impl;

import com.via.content.app.skill.repository.SkillRepository;
import com.via.content.domain.course.model.Skill;
import com.via.content.infra.skill.database.entity.SkillJpaEntity;
import com.via.content.infra.skill.database.repository.SkillJpaRepository;
import com.via.content.infra.skill.mapper.SkillMapper;
import com.via.content.infra.skill.search.repository.SkillDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SkillRepositoryImpl implements SkillRepository {
    private final SkillMapper mapper;

    private final SkillJpaRepository skillJpaRepository;
    private final SkillDocumentRepository skillDocumentRepository;

    @Override
    public Skill save(Skill skill) {
        SkillJpaEntity savedEntity = skillJpaRepository.save(mapper.toEntity(skill));
        skillDocumentRepository.save(mapper.toDocument(savedEntity));
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Skill> searchSimilar(String name) {
        return skillDocumentRepository.findTopBySimilarity(name).map(mapper::toDomain);
    }

    @Override
    public List<Skill> saveAll(List<Skill> skillList) {
        List<SkillJpaEntity> savedEntity = skillJpaRepository.saveAll(
                skillList.stream().map(mapper::toEntity).toList()
        );
        skillDocumentRepository.saveAll(
                savedEntity.stream().map(mapper::toDocument).toList()
        );
        return savedEntity.stream().map(mapper::toDomain).toList();
    }
}

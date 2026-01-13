package com.via.content.infra.skill.search.repository.custom;

import com.via.content.infra.skill.search.entity.SkillDocument;

import java.util.Optional;

public interface SkillDocumentRepositoryCustom {
    Optional<SkillDocument> findTopBySimilarity(String name);
}

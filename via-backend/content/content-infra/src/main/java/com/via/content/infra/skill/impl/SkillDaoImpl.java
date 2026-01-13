package com.via.content.infra.skill.impl;

import com.via.content.app.skill.dao.SkillDao;
import com.via.content.app.skill.dto.SkillInfo;
import com.via.content.infra.skill.search.entity.AliasEmbedding;
import com.via.content.infra.skill.search.repository.SkillDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SkillDaoImpl implements SkillDao {
    private final SkillDocumentRepository skillDocumentRepository;

    @Override
    public Page<SkillInfo> findByName(String name, Pageable pageable) {
        return skillDocumentRepository.findByName(name, pageable)
                .map(doc -> new SkillInfo(doc.getId(), doc.getName(),
                        doc.getAliasEmbeddings().stream()
                                .map(AliasEmbedding::alias)
                                .toList()));
    }
}

package com.via.content.app.skill.service;

import com.via.content.app.skill.dao.SkillDao;
import com.via.content.app.skill.dto.CreateSkill;
import com.via.content.app.skill.dto.SkillInfo;
import com.via.content.app.skill.repository.SkillRepository;
import com.via.content.domain.course.model.Skill;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SkillUseCase {
    private final SkillRepository skillRepository;
    private final SkillDao skillDao;

    @Transactional
    public void create(List<CreateSkill> createSkillList) {
        List<Skill> skills = createSkillList.stream()
                        .map(createSkill -> Skill.create(createSkill.name(), createSkill.alias()))
                        .toList();
        skillRepository.saveAll(skills);
    }

    public Page<SkillInfo> searchSkill(String name, Pageable pageable) {
        return skillDao.findByName(name, pageable);
    }
}

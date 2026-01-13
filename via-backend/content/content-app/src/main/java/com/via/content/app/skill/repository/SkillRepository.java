package com.via.content.app.skill.repository;


import com.via.content.domain.course.model.Skill;

import java.util.List;
import java.util.Optional;

public interface SkillRepository {
    Skill save(Skill skill);
    Optional<Skill> searchSimilar(String name);
    List<Skill> saveAll(List<Skill> skillList);
}

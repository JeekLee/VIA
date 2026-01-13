package com.via.content.infra.skill.database.repository;

import com.via.content.infra.skill.database.entity.SkillJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillJpaRepository extends JpaRepository<SkillJpaEntity, Long> {

}

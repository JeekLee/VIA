package com.via.content.app.skill.dao;

import com.via.content.app.skill.dto.SkillInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SkillDao {
    Page<SkillInfo> findByName(String name, Pageable pageable);
}

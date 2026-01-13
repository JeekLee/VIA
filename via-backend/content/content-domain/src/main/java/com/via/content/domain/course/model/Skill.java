package com.via.content.domain.course.model;

import com.via.content.domain.course.model.id.SkillId;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Builder
public record Skill(
        SkillId id,
        String name,
        List<String> aliases,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static Skill create(String name, List<String> aliases) {
        return Skill.builder()
                .name(name)
                .aliases(aliases)
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .updatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }
}

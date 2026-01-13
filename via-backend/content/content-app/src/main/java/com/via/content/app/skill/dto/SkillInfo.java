package com.via.content.app.skill.dto;

import java.util.List;

public record SkillInfo(
        Long id,
        String name,
        List<String> aliases
) {

}

package com.via.content.app.skill.dto;

import java.util.List;

public record CreateSkill(
        String name,
        List<String> alias
) {

}

package com.via.content.api.controller;

import com.via.content.api.api.SkillApi;
import com.via.content.app.skill.dto.CreateSkill;
import com.via.content.app.skill.dto.SkillInfo;
import com.via.content.app.skill.service.SkillUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SkillController implements SkillApi {
    private final SkillUseCase skillUseCase;

    @Override
    public ResponseEntity<Void> createSkills(List<CreateSkill> request) {
        skillUseCase.create(request);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Page<SkillInfo>> searchSkill(String name, Pageable pageable) {
        return ResponseEntity.ok(skillUseCase.searchSkill(name, pageable));
    }
}

package com.via.content.api.api;

import com.via.content.app.skill.dto.CreateSkill;
import com.via.content.app.skill.dto.SkillInfo;
import com.via.support.security.annotation.RequireAuthority;
import com.via.support.security.enums.Authority;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Skill", description = "Skill API")
@RequestMapping("/skill")
@Validated
public interface SkillApi {
    @Operation(
            summary = "Create Skill",
            description = """
                     ## Key Features

                     Creates new skills.
                     Created skill metadata is stored in both the master database (MySQL) and the index database (Opensearch).

                     The index database uses nori, ngram analyzer and lucene engine based cosinesimil.

                     This feature is only available to administrators.
                     """
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Skill Creation Example",
                                    externalValue = "/swagger/skill-creation-example.json"
                            )
                    }
            )
    )
    @PostMapping("")
    @RequireAuthority(Authority.MANAGER)
    ResponseEntity<Void> createSkills(@RequestBody List<CreateSkill> request);

    @Operation(
            summary = "Search Skill",
            description = """
                     ## Key Features

                     Searches skills. Uses tokens analyzed by nori and n-gram.

                     This feature is only available to users.
                     """
    )
    @GetMapping("/search")
    @RequireAuthority(Authority.USER)
    ResponseEntity<Page<SkillInfo>> searchSkill(@RequestParam String name, @ParameterObject Pageable pageable);
}

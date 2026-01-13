package com.via.common.api.api;

import com.via.common.app.career.dto.UniversityInfo;
import com.via.support.security.annotation.RequireAuthority;
import com.via.support.security.enums.Authority;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "University", description = "University API")
@RequestMapping("/university")
@Validated
public interface UniversityApi {

    @Operation(
            summary = "Search university",
            description = """
                    ## Key Features

                    Searches universities by name.

                    Only regular users can access this.
                    """
    )
    @GetMapping("/search")
    @RequireAuthority(Authority.USER)
    ResponseEntity<Page<UniversityInfo>> searchUniversity(@RequestParam String name, @ParameterObject Pageable pageable);
}

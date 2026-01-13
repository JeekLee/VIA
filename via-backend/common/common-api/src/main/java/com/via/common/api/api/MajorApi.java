package com.via.common.api.api;

import com.via.common.app.career.dto.MajorInfo;
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

@Tag(name = "Major", description = "Major API")
@RequestMapping("/major")
@Validated
public interface MajorApi {

    @Operation(
            summary = "Search major",
            description = """
                    ## Key Features

                    Searches majors by name.

                    Only regular users can access this.
                    """
    )
    @GetMapping("/search")
    @RequireAuthority(Authority.USER)
    ResponseEntity<Page<MajorInfo>> searchUniversity(@RequestParam String name, @ParameterObject Pageable pageable);
}

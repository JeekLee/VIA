package com.via.common.api.api;

import com.via.common.api.request.CreateCareerRequest;
import com.via.common.api.request.UpdateCareerImageRequest;
import com.via.common.api.request.UpdateCareerRequest;
import com.via.common.app.career.dto.CareerInfo;
import com.via.support.security.annotation.CurrentUserId;
import com.via.support.security.annotation.RequireAuthority;
import com.via.support.security.enums.Authority;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "MyCareer", description = "My Career API")
@RequestMapping("/career/me")
@Validated
public interface MyCareerApi {

    @Operation(
            summary = "Create career",
            description = """
                    ## Key Features

                    Creates user's career information.

                    Only regular users can access this.
                    """
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RequireAuthority(Authority.USER)
    ResponseEntity<Void> createCareer(
            @CurrentUserId Long userId,
            @Valid @ModelAttribute CreateCareerRequest request
    );

    @Operation(
            summary = "Get career info",
            description = """
                    ## Key Features

                    Retrieves user's career information.

                    Only regular users can access this.
                    """
    )
    @GetMapping
    @RequireAuthority(Authority.USER)
    ResponseEntity<CareerInfo> getCareerInfo(
            @CurrentUserId Long userId
    );

    @Operation(
            summary = "Update career information",
            description = """
                    ## Key Features

                    Partially updates user's career information.

                    Fields not provided will retain their existing values.

                    Only regular users can access this.
                    """
    )
    @PatchMapping
    @RequireAuthority(Authority.USER)
    ResponseEntity<Void> updateCareer(
            @CurrentUserId Long userId,
            @RequestBody @Validated UpdateCareerRequest request
    );

    @Operation(
            summary = "Update career image",
            description = """
                    ## Key Features

                    Updates user's career image.

                    Only regular users can access this.
                    """
    )
    @PatchMapping(path = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RequireAuthority(Authority.USER)
    ResponseEntity<Void> updateCareerImage(
            @CurrentUserId Long userId,
            @Valid @ModelAttribute UpdateCareerImageRequest request
    );
}

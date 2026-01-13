package com.via.common.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UpdateCareerImageRequest {
    @Schema(description = "Profile image")
    @NotNull(message = "Image is required")
    private MultipartFile image;
}

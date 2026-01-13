package com.via.common.api.request;

import com.via.common.domain.career.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "Career creation request")
public class CreateCareerRequest {
    @Schema(description = "Profile image")
    @NotNull(message = "Image is required")
    private MultipartFile image;

    @Schema(description = "Name", example = "John Doe")
    @NotBlank(message = "Name is required")
    private String name;

    @Schema(description = "Birth date", example = "1990-01-01")
    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;

    @Schema(description = "Gender", example = "MALE")
    @NotNull(message = "Gender is required")
    private Gender gender;

    @Schema(description = "Phone number", example = "010-1234-5678")
    @NotNull(message = "Phone number is required")
    private String phone;

    @Schema(description = "Email", example = "john@example.com")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(description = "Zip code", example = "06236")
    @NotNull(message = "Zip code is required")
    private String zipCode;

    @Schema(description = "Road address", example = "123 Main Street")
    @NotNull(message = "Road address is required")
    private String roadAddress;

    @Schema(description = "Detail address", example = "Building A, Room 101")
    @NotNull(message = "Detail address is required")
    private String detailAddress;
}

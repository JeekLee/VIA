package com.via.common.api.request;

import com.via.common.domain.career.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateCareerRequest {
    @Schema(description = "Name", example = "John Doe")
    private String name;

    @Schema(description = "Birth date", example = "1990-01-01")
    private LocalDate birthDate;

    @Schema(description = "Gender", example = "MALE")
    private Gender gender;

    @Schema(description = "Phone number", example = "010-1234-5678")
    private String phone;

    @Schema(description = "Email", example = "john@example.com")
    private String email;

    @Schema(description = "Zip code", example = "06236")
    private String zipCode;

    @Schema(description = "Road address", example = "123 Main Street")
    private String roadAddress;

    @Schema(description = "Detail address", example = "Building A, Room 101")
    private String detailAddress;
}

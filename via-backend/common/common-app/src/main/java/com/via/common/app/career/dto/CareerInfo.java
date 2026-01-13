package com.via.common.app.career.dto;

import com.via.common.domain.career.enums.Gender;

import java.time.LocalDate;

public record CareerInfo(
        Long memberId,
        String imagePath,
        String name,
        LocalDate birthDate,
        Gender gender,
        String phone,
        String email,
        String zipCode,
        String roadAddress,
        String detailAddress
) {

}

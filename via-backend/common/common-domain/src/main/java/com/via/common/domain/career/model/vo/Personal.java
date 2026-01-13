package com.via.common.domain.career.model.vo;

import com.via.common.domain.career.enums.Gender;

import java.time.LocalDate;

public record Personal(
        String name,
        LocalDate birthDate,
        Gender gender
) {
    public Personal update(String name, LocalDate birthDate, Gender gender) {
        return new Personal(
                name != null ? name : this.name,
                birthDate != null ? birthDate : this.birthDate,
                gender != null ? gender : this.gender
        );
    }
}

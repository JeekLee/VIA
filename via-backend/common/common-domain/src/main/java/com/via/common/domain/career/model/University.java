package com.via.common.domain.career.model;

import com.via.common.domain.career.model.id.UniversityId;
import lombok.Builder;

@Builder
public record University(
        UniversityId id,
        String name
) {
    public static University create(String code, String name) {
        return new University(new UniversityId(code), name);
    }
}

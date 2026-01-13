package com.via.common.domain.career.model;

import com.via.common.domain.career.model.id.MajorId;
import lombok.Builder;

@Builder
public record Major(
        MajorId  id,
        String name
) {
    public static Major create(String code, String name) {
        return new Major(new MajorId(code), name);
    }
}

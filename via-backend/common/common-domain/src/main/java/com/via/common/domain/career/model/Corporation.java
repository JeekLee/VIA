package com.via.common.domain.career.model;

import com.via.common.domain.career.model.id.CorporationId;

public record Corporation(
        CorporationId id,
        String name
) {
    public static Corporation create(String code, String name) {
        return new Corporation(new CorporationId(code), name);
    }
}

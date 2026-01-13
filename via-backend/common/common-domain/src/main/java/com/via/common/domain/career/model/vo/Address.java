package com.via.common.domain.career.model.vo;

public record Address(
        String zipCode,
        String road,
        String detail
) {
    public Address update(String zipCode, String road, String detail) {
        return new Address(
                zipCode != null ? zipCode : this.zipCode,
                road != null ? road : this.road,
                detail != null ? detail : this.detail
        );
    }
}

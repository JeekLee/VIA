package com.via.common.domain.career.model.vo;

public record Contact(
        String phone,
        String email
) {
    public Contact update(String phone, String email) {
        return new Contact(
                phone != null ? phone : this.phone,
                email != null ? email : this.email
        );
    }
}

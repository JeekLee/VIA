package com.via.account.domain.model.id;

public record MemberId(long id) {
    @Override
    public String toString() {
        return String.valueOf(id);
    }
}

package com.via.account.domain.model.vo;

public record MemberImage(String path) {
    public static MemberImage empty() {
        return new MemberImage(null);
    }
}

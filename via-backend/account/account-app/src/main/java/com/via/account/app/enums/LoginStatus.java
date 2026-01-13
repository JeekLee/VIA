package com.via.account.app.enums;

public enum LoginStatus {
    ALLOWED,
    BLOCKED_NEW_MEMBER,
    BLOCKED_TERMS_REQUIRED,
    BLOCKED_PROFILE_REQUIRED,
    BLOCKED_RESIGN_REQUESTED
    ;

    public boolean isBlocked() { return this != ALLOWED; }
}

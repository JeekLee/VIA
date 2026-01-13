package com.via.account.api.response;

import com.via.account.app.dto.LoginContext;
import com.via.account.app.enums.LoginStatus;

public record LoginResponse(
        String redirectPath,
        LoginStatus status
) {
    public static LoginResponse fromContext(LoginContext session) {
        return new LoginResponse(session.redirectPath(), session.status());
    }
}

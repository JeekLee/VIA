package com.via.account.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNicknameRequest {
    @NotBlank(message = "Nickname is required")
    @Pattern(
            regexp = "^[가-힣A-Za-z._-]{1,15}$",
            message = "Nickname can only contain Korean, English, and special characters (_, -, .) up to 15 characters"
    )
    private String nickname;
}

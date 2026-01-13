package com.via.account.api.api;

import com.via.support.security.annotation.CurrentUserId;
import com.via.support.security.annotation.RequireAuthority;
import com.via.support.security.enums.Authority;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Terms", description = "사용자 약관 API")
@RequestMapping("/terms")
@Validated
public interface TermsConsentApi {
    @Operation(
            summary = "Agree terms of service",
            description = """
                    ## ✨ 주요 기능
                   
                    사용자 약관에 동의합니다.
                   
                    임시 사용자 및 일반 사용자 접근이 가능합니다.
                    """
    )
    @GetMapping(path = "/agree")
    @RequireAuthority({Authority.USER, Authority.TEMP})
    ResponseEntity<Void> agreeTermsOfService(@CurrentUserId Long userId, @RequestParam("termsIdList")List<Long> termsIdList);

    @Operation(
            summary = "Withdraw from terms of service",
            description = """
                    ## ✨ 주요 기능

                    사용자 약관을 철회합니다.

                    임시 사용자 및 일반 사용자 접근이 가능합니다.
                    """
    )
    @GetMapping(path = "/withdraw")
    @RequireAuthority({Authority.USER, Authority.TEMP})
    ResponseEntity<Void> withdrawTermsOfService(@CurrentUserId Long userId, @RequestParam("termsIdList")List<Long> termsIdList);
}

package com.via.common.api.api;

import com.via.common.app.onboarding.dto.OnboardingInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Onboarding", description = "Onboarding information API for service users")
@RequestMapping("/onboarding")
@Validated
public interface OnboardingInfoApi {
    @Operation(
            summary = "Get onboarding information",
            description = """
                     ## Key Features

                     Returns onboarding information to be displayed to users.

                     By default, only active onboarding information is returned, sorted by priority (closer to 0 is higher priority).

                     No authentication/authorization required as this information needs to be exposed to all users.
                     """
    )
    @GetMapping("/info")
    ResponseEntity<List<OnboardingInfo>> getOnboardingDocuments();
}

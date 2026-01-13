package com.via.account.api.api;

import com.via.account.app.dto.MemberInfo;
import com.via.support.security.annotation.CurrentUserId;
import com.via.support.security.annotation.RequireAuthority;
import com.via.support.security.enums.Authority;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Member", description = "회원 API")
@RequestMapping("/member")
@Validated
public interface MemberApi {
    @Operation(
            summary = "Get member information of current user",
            description = """
                    ## Key Features
                   
                    Get member information of current user.
                    
                    Uses `AccessToken` and `TemporaryToken` to get user ID.
                    
                    Can access with authority `USER`, `TEMP`
                    """
    )
    @GetMapping(path = "/me")
    @RequireAuthority({Authority.USER, Authority.TEMP})
    ResponseEntity<MemberInfo> getMyMemberInfo(@CurrentUserId Long userId);
}

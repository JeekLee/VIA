package com.via.account.api.api;

import com.via.account.app.dto.TermsInfo;
import com.via.support.security.annotation.RequireAuthority;
import com.via.support.security.enums.Authority;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Terms", description = "사용자 약관 API")
@RequestMapping("/terms")
@Validated
public interface TermsInfoApi {
    @Operation(
            summary = "Get terms of service information",
            description = """
                    ## ✨ 주요 기능
                   
                    사용자 약관 정보를 조회합니다.
                   
                    현재 존재하는 약관 중, 가장 최신 버전이면서 시행 기간이 지난 약관들을 반환합니다.
                   
                    임시 사용자 및 일반 사용자 접근이 가능합니다.
                    """
    )
    @GetMapping(path = "/info")
    @RequireAuthority({Authority.USER, Authority.TEMP})
    ResponseEntity<List<TermsInfo>> getTermsOfService();
}

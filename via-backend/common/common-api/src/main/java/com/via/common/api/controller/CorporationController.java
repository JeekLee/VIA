package com.via.common.api.controller;

import com.via.common.api.api.CorporationApi;
import com.via.common.app.career.dto.CorporationInfo;
import com.via.common.app.career.service.CorporationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CorporationController implements CorporationApi {
    private final CorporationUseCase corporationUseCase;

    @Override
    public ResponseEntity<Page<CorporationInfo>> searchCorporation(String name, Pageable pageable) {
        return ResponseEntity.ok(corporationUseCase.searchCorporation(name, pageable));
    }
}

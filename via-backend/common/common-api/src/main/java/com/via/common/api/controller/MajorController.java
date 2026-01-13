package com.via.common.api.controller;

import com.via.common.api.api.MajorApi;
import com.via.common.app.career.dto.MajorInfo;
import com.via.common.app.career.service.MajorUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MajorController implements MajorApi {
    private final MajorUseCase majorUseCase;

    @Override
    public ResponseEntity<Page<MajorInfo>> searchUniversity(String name, Pageable pageable) {
        return ResponseEntity.ok(majorUseCase.searchMajor(name, pageable));
    }
}

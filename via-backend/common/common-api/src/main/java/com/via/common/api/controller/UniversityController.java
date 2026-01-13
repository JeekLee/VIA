package com.via.common.api.controller;

import com.via.common.api.api.UniversityApi;
import com.via.common.app.career.dto.UniversityInfo;
import com.via.common.app.career.service.UniversityUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UniversityController implements UniversityApi {
    private final UniversityUseCase universityUseCase;

    @Override
    public ResponseEntity<Page<UniversityInfo>> searchUniversity(String name, Pageable pageable) {
        return ResponseEntity.ok(universityUseCase.searchUniversity(name, pageable));
    }
}

package com.via.account.api.controller;

import com.via.account.api.api.TermsInfoApi;
import com.via.account.app.dto.TermsInfo;
import com.via.account.app.service.TermsInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TermsInfoController implements TermsInfoApi {
    private final TermsInfoService termsInfoService;

    @Override
    public ResponseEntity<List<TermsInfo>> getTermsOfService() {
        return ResponseEntity.ok(termsInfoService.getLatestTerms());
    }
}

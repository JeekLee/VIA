package com.via.runnable.batch.rest.controller;

import com.via.runnable.batch.common.scheduler.CorporationSyncScheduler;
import com.via.runnable.batch.common.scheduler.MajorSyncScheduler;
import com.via.runnable.batch.common.scheduler.UniversitySyncScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/common")
@RequiredArgsConstructor
public class CommonController {
    private final CorporationSyncScheduler corporationSyncScheduler;
    private final MajorSyncScheduler majorSyncScheduler;
    private final UniversitySyncScheduler universitySyncScheduler;

    @PostMapping("/sync/corporation")
    public ResponseEntity<Void> syncCorporation() {
        CompletableFuture.runAsync(corporationSyncScheduler::runUpdateCorporation);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/sync/major")
    public ResponseEntity<Void> syncMajor() {
        CompletableFuture.runAsync(majorSyncScheduler::runUpdateMajor);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/sync/university")
    public ResponseEntity<Void> syncUniversity() {
        CompletableFuture.runAsync(universitySyncScheduler::runUpdateUniversityDocument);
        return ResponseEntity.accepted().build();
    }
}

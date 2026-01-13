package com.via.runnable.batch.rest.controller;

import com.via.runnable.batch.content.scheduler.CourseScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
public class ContentController {
    private final CourseScheduler courseScheduler;

    @PostMapping("/update/course")
    public ResponseEntity<Void> updateCourseEntity() {
        CompletableFuture.runAsync(courseScheduler::runUpdateCourseEntity);
        return ResponseEntity.accepted().build();
    }
}

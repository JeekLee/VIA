package com.via.content.api.controller;

import com.via.content.api.api.CourseApi;
import com.via.content.app.course.service.CourseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CourseController implements CourseApi {
    private final CourseUseCase courseUseCase;

    @Override
    public ResponseEntity<List<String>> searchCourseByVector(String query) {
        return ResponseEntity.ok().body(courseUseCase.searchSimilarAndConvertAsString(query));
    }
}

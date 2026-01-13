package com.via.content.api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Course", description = "Course API")
@RequestMapping("/course")
@Validated
public interface CourseApi {
    @Operation(
            summary = "Search Course with Vector",
            description = """
                     ## Key Features

                     Performs k-nn algorithm based search.

                     This feature is open for non-developer testing. Will be incorporated as a hard-coded feature in the future.
                     """
    )
    @GetMapping("/search/vector")
    ResponseEntity<List<String>> searchCourseByVector(@RequestParam String query);
}

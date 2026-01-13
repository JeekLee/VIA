package com.via.content.app.course.dto;

import com.via.content.domain.course.enums.CourseDifficulty;
import com.via.content.domain.course.enums.CoursePlatform;

import java.net.URI;
import java.util.List;

public record CreateCourse(
        URI url,
        CoursePlatform platform,
        String title,
        String instructor,
        String description,
        CourseDifficulty difficulty,
        Float rating,
        List<String> skillNames
) {

}

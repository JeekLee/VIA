package com.via.content.crawler.dto;

import com.via.content.domain.course.enums.CourseDifficulty;
import com.via.content.domain.course.enums.CoursePlatform;

import java.net.URI;
import java.util.List;

public record CrawledCourse(
        URI url,
        CoursePlatform platform,
        String imageUrl,
        String title,
        String instructor,
        String description,
        CourseDifficulty difficulty,
        Float rating,
        List<String> skillNames
) {

}

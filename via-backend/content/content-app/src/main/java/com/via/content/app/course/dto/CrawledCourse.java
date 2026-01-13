package com.via.content.app.course.dto;

import com.via.content.domain.course.model.Course;

import java.util.List;

public record CrawledCourse(
        Course course,
        List<String> skillsAsString
) {

}

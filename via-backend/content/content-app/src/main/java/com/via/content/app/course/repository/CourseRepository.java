package com.via.content.app.course.repository;

import com.via.content.domain.course.model.Course;

import java.util.List;

public interface CourseRepository {
    List<Course> saveAll(List<Course> courses);
    List<Course> searchSimilarByQuery(String input);
}

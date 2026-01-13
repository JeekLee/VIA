package com.via.content.infra.course.database.repository.custom;

import com.via.content.domain.course.model.Course;

import java.util.List;

public interface CourseJpaRepositoryCustom {
    List<Course> saveAll(List<Course> courses);
}

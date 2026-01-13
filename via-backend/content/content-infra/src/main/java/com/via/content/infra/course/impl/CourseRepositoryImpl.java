package com.via.content.infra.course.impl;

import com.via.content.app.course.repository.CourseRepository;
import com.via.content.domain.course.model.Course;
import com.via.content.infra.course.database.entity.CourseJpaEntity;
import com.via.content.infra.course.database.repository.CourseJpaRepository;
import com.via.content.infra.course.mapper.CourseMapper;
import com.via.content.infra.course.search.entity.CourseDocument;
import com.via.content.infra.course.search.repository.CourseDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseRepositoryImpl implements CourseRepository {
    private final CourseJpaRepository courseJpaRepository;
    private final CourseDocumentRepository courseDocumentRepository;
    private final CourseMapper mapper;

    @Override
    public List<Course> saveAll(List<Course> courses) {
        List<Course> savedCourses = courseJpaRepository.saveAll(courses);
        courseDocumentRepository.saveAll(mapper.toDocuments(savedCourses));
        return savedCourses;
    }

    @Override
    public List<Course> searchSimilarByQuery(String input) {
        List<CourseDocument> courseDocuments = courseDocumentRepository.searchSimilarByQuery(input);
        if (courseDocuments.isEmpty()) return List.of();
        List<Long> ids = courseDocuments.stream().map(CourseDocument::getId).toList();
        List<CourseJpaEntity> courseJpaEntities = courseJpaRepository.findByIdIn(ids);
        return courseJpaEntities.stream().map(mapper::toDomain).toList();
    }
}

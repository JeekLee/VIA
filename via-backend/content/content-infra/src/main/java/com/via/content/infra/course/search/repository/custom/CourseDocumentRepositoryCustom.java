package com.via.content.infra.course.search.repository.custom;

import com.via.content.infra.course.search.entity.CourseDocument;

import java.util.List;

public interface CourseDocumentRepositoryCustom {
    List<CourseDocument> searchSimilarByQuery(String query);
}

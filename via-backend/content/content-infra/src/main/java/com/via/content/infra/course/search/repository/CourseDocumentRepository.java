package com.via.content.infra.course.search.repository;

import com.via.content.infra.course.search.entity.CourseDocument;
import com.via.content.infra.course.search.repository.custom.CourseDocumentRepositoryCustom;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CourseDocumentRepository extends ElasticsearchRepository<CourseDocument, Long>, CourseDocumentRepositoryCustom {
}

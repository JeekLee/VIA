package com.via.content.infra.course.database.repository;

import com.via.content.infra.course.database.entity.CourseJpaEntity;
import com.via.content.infra.course.database.repository.custom.CourseJpaRepositoryCustom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseJpaRepository extends JpaRepository<CourseJpaEntity, Long>, CourseJpaRepositoryCustom {
    @EntityGraph(attributePaths = {"skills"})
    List<CourseJpaEntity> findByIdIn(List<Long> ids);
}

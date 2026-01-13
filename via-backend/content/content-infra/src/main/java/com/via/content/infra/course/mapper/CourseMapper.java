package com.via.content.infra.course.mapper;

import com.via.content.domain.course.model.Course;
import com.via.content.domain.course.model.Skill;
import com.via.content.domain.course.model.id.CourseId;
import com.via.content.infra.course.database.entity.CourseJpaEntity;
import com.via.content.infra.course.search.entity.CourseDocument;
import com.via.content.infra.skill.mapper.SkillMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseMapper {
    private final EmbeddingModel embeddingModel;
    private final SkillMapper skillMapper;

    public CourseJpaEntity toEntity(Course course) {
        return CourseJpaEntity.builder()
                .id(course.id() != null ? course.id().id() : null)
                .url(course.url().toString())
                .platform(course.platform())
                .title(course.title())
                .instructor(course.instructor())
                .description(course.description())
                .difficulty(course.difficulty())
                .rating(course.rating())
                .createdAt(course.createdAt())
                .updatedAt(course.updatedAt())
                .build();
    }

    public CourseDocument toDocument(Course course) {
        return CourseDocument.builder()
                .id(course.id().id())
                .text(course.toString())
                .textEmbedding(embeddingModel.embed(course.toString()))
                .createdAt(course.createdAt())
                .updatedAt(course.updatedAt())
                .build();
    }

    public List<CourseDocument> toDocuments(List<Course> courses) {
        int batchSize = 200;        // OpenAI API recommendation: 100~500, max 2048

        List<float[]> embeddings = new ArrayList<>();
        for (int i = 0; i < courses.size(); i += batchSize) {
            int end = Math.min(i + batchSize, courses.size());
            List<Course> batch = courses.subList(i, end);

            List<String> texts = batch.stream().map(Course::toString).toList();
            embeddings.addAll(embeddingModel.embed(texts));
        }

        List<CourseDocument> courseDocuments = new ArrayList<>();
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            courseDocuments.add(CourseDocument.builder()
                    .id(course.id().id())
                    .text(course.toString())
                    .textEmbedding(embeddings.get(i))
                    .createdAt(course.createdAt())
                    .updatedAt(course.updatedAt())
                    .build());
        }

        return courseDocuments;
    }

    public Course toDomain(CourseJpaEntity entity) {
        List<Skill> skills = entity.getSkills().stream().map(skillMapper::toDomain).toList();

        return Course.builder()
                .id(new CourseId(entity.getId()))
                .url(URI.create(entity.getUrl()))
                .platform(entity.getPlatform())
                .title(entity.getTitle())
                .instructor(entity.getInstructor())
                .description(entity.getDescription())
                .difficulty(entity.getDifficulty())
                .rating(entity.getRating())
                .skills(skills)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

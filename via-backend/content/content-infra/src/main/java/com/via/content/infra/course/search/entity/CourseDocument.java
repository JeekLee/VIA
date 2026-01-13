package com.via.content.infra.course.search.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;

@Document(indexName = "course")
@Mapping(mappingPath = "search/course-mapping.json")
@Setting(settingPath = "search/course-setting.json")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseDocument {
    
    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String text;

    private float[] textEmbedding;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime createdAt;
    
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime updatedAt;

    @Builder
    public CourseDocument(Long id, String text, float[] textEmbedding, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.text = text;
        this.textEmbedding = textEmbedding;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
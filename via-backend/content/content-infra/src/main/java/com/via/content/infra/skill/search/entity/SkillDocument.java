package com.via.content.infra.skill.search.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Document(indexName = "skill")
@Mapping(mappingPath = "search/skill-mapping.json")
@Setting(settingPath = "search/skill-setting.json")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SkillDocument {
    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String name;

    private List<AliasEmbedding> aliasEmbeddings;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime updatedAt;

    @Builder
    public SkillDocument(Long id, String name, List<AliasEmbedding> aliasEmbeddings, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.aliasEmbeddings = aliasEmbeddings;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
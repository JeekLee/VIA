package com.via.common.infra.career.search.document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Document(indexName = "university")
@Mapping(mappingPath = "search/university-mapping.json")
@Setting(settingPath = "search/university-setting.json")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UniversityDocument {
    @Id
    private String code;

    @Field(type = FieldType.Text)
    private String name;

    @Builder
    public UniversityDocument(String code, String name) {
        this.code = code;
        this.name = name;
    }
}

package com.via.common.infra.career.search.document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Document(indexName = "major")
@Mapping(mappingPath = "search/major-mapping.json")
@Setting(settingPath = "search/major-setting.json")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MajorDocument {
    @Id
    private String code;

    @Field(type = FieldType.Text)
    private String name;

    @Builder
    public MajorDocument(String code, String name) {
        this.code = code;
        this.name = name;
    }
}

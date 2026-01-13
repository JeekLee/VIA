package com.via.common.infra.career.search.document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Document(indexName = "corporation")
@Mapping(mappingPath = "search/corporation-mapping.json")
@Setting(settingPath = "search/corporation-setting.json")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CorporationDocument {
    @Id
    private String code;

    @Field(type = FieldType.Text)
    private String name;

    @Builder
    public CorporationDocument(String code, String name) {
        this.code = code;
        this.name = name;
    }
}

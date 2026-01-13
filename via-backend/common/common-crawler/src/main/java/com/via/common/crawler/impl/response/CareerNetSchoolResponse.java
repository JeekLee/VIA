package com.via.common.crawler.impl.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class CareerNetSchoolResponse {

    @JsonProperty("dataSearch")
    private DataSearch dataSearch;

    @Getter
    @NoArgsConstructor
    @ToString
    public static class DataSearch {

        @JsonProperty("content")
        private List<SchoolInfo> content = new ArrayList<>();

        @JsonProperty("totalCount")
        private String totalCount;
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class SchoolInfo {

        @JsonProperty("seq")
        private String seq;  // Sequence number

        @JsonProperty("schoolGubun")
        private String schoolGubun;  // School classification (e.g., 4-year university)

        @JsonProperty("schoolName")
        private String schoolName;  // School name

        @JsonProperty("campusName")
        private String campusName;  // Campus name

        @JsonProperty("schoolType")
        private String schoolType;  // School type (e.g., general university)

        @JsonProperty("estType")
        private String estType;  // Establishment type (e.g., private)

        @JsonProperty("region")
        private String region;  // Region

        @JsonProperty("adres")
        private String adres;  // Address

        @JsonProperty("collegeinfourl")
        private String collegeinfourl;  // MyCollege connection info

        @JsonProperty("link")
        private String link;  // Link URL

        @JsonProperty("totalCount")
        private Integer totalCount;  // Total search result count
    }
}

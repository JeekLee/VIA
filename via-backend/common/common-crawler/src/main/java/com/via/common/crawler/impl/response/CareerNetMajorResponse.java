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
public class CareerNetMajorResponse {

    @JsonProperty("dataSearch")
    private DataSearch dataSearch;

    @Getter
    @NoArgsConstructor
    @ToString
    public static class DataSearch {

        @JsonProperty("content")
        private List<MajorInfo> content = new ArrayList<>();

    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class MajorInfo {

        @JsonProperty("majorSeq")
        private String seq;  // Major code

        @JsonProperty("lClass")
        private String lClass;  // Field/Department category

        @JsonProperty("mClass")
        private String mClass;  // Major

        @JsonProperty("totalCount")
        private Integer totalCount;  // Total search result count
    }
}

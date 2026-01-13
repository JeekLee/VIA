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
public class FssCorporationResponse {

    @JsonProperty("response")
    private Response response;

    @Getter
    @NoArgsConstructor
    @ToString
    public static class Response {

        @JsonProperty("header")
        private Header header;

        @JsonProperty("body")
        private Body body;
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class Header {

        @JsonProperty("resultCode")
        private String resultCode;

        @JsonProperty("resultMsg")
        private String resultMsg;
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class Body {

        @JsonProperty("items")
        private Items items;

        @JsonProperty("numOfRows")
        private Integer numOfRows;

        @JsonProperty("pageNo")
        private Integer pageNo;

        @JsonProperty("totalCount")
        private Integer totalCount;
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class Items {

        @JsonProperty("item")
        private List<CorporationInfo> item = new ArrayList<>();
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class CorporationInfo {

        @JsonProperty("crno")
        private String crno;  // Corporation registration number

        @JsonProperty("corpNm")
        private String corpNm;  // Corporation name
    }
}

package com.via.common.crawler.impl.client;

import com.via.common.crawler.impl.response.FssCorporationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "fssCompanyApi", url = "${app.api.fss-corporation-basic-info.api-url}")
public interface FssCorporationClient {
    @GetMapping("/getCorpOutline_V2")
    FssCorporationResponse getCompanyList(
            @RequestParam("serviceKey") String serviceKey,
            @RequestParam("pageNo") Integer pageNo,
            @RequestParam("numOfRows") Integer numOfRows,
            @RequestParam(value = "resultType", defaultValue = "json") String resultType,
            @RequestParam(value = "crno", required = false) String crno,
            @RequestParam(value = "corpNm", required = false) String corpNm
    );
}
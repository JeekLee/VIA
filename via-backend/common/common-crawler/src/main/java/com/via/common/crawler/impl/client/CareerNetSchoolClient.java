package com.via.common.crawler.impl.client;

import com.via.common.crawler.impl.response.CareerNetSchoolResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "careerNetSchoolClient", url = "${app.api.career-net.api-url}")
public interface CareerNetSchoolClient {
    @GetMapping
    @Headers("Content-Type: application/json")
    CareerNetSchoolResponse getUniversityResponse (
            @RequestParam("apiKey") String apiKey,
            @RequestParam("svcType") String svcType,
            @RequestParam("svcCode") String svcCode,
            @RequestParam("contentType") String contentType,
            @RequestParam("gubun") String gubun,
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "sch1", required = false) String sch1,
            @RequestParam(value = "sch2", required = false) String sch2,
            @RequestParam(value = "est", required = false) String est,
            @RequestParam(value = "thisPage", required = false) Integer thisPage,
            @RequestParam(value = "perPage", required = false) Integer perPage,
            @RequestParam(value = "searchSchulNm", required = false) String searchSchulNm
    );
}

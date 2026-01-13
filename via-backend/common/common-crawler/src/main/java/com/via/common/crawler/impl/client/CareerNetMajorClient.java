package com.via.common.crawler.impl.client;

import com.via.common.crawler.impl.response.CareerNetMajorResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "careerNetMajorClient", url = "${app.api.career-net.api-url}")
public interface CareerNetMajorClient {
    @GetMapping
    @Headers("Content-Type: application/json")
    CareerNetMajorResponse getMajorResponse (
            @RequestParam("apiKey") String apiKey,
            @RequestParam("svcType") String svcType,
            @RequestParam("svcCode") String svcCode,
            @RequestParam("contentType") String contentType,
            @RequestParam("gubun") String gubun,
            @RequestParam(value = "subject", required = false) String subject,
            @RequestParam(value = "thisPage", required = false) Integer thisPage,
            @RequestParam(value = "perPage", required = false) Integer perPage,
            @RequestParam(value = "searchTitle", required = false) String searchTitle
    );
}

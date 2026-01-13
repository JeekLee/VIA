package com.via.common.crawler.impl;

import com.via.common.crawler.UniversityCrawler;
import com.via.common.crawler.dto.CrawledUniversity;
import com.via.common.crawler.impl.client.CareerNetSchoolClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CareerNetUniversityCrawlerImpl implements UniversityCrawler {
    private final CareerNetSchoolClient careerNetSchoolClient;

    @Value("${app.api.career-net.api-key}")
    private String apiKey;

    private static final String SVC_TYPE = "api";
    private static final String SVC_CODE = "SCHOOL";
    private static final String CONTENT_TYPE = "json";
    private static final String GUBUN = "univ_list";

    @Override
    public List<CrawledUniversity> findAll(int page, int pageSize) {
        return careerNetSchoolClient.getUniversityResponse(
                apiKey,
                SVC_TYPE,
                SVC_CODE,
                CONTENT_TYPE,
                GUBUN,
                null,
                null,
                null,
                null,
                page,
                pageSize,
                null
        ).getDataSearch().getContent()
                .stream()
                .map(universityInfo -> new CrawledUniversity(universityInfo.getSeq(), universityInfo.getSchoolName()))
                .toList();
    }

    @Override
    public int getTotalCount() {
        return careerNetSchoolClient.getUniversityResponse(
                apiKey,
                SVC_TYPE,
                SVC_CODE,
                CONTENT_TYPE,
                GUBUN,
                null,
                null,
                null,
                null,
                1,
                1,
                null
        ).getDataSearch().getContent().getFirst().getTotalCount();
    }
}

package com.via.common.crawler.impl;

import com.via.common.crawler.MajorCrawler;
import com.via.common.crawler.dto.CrawledMajor;
import com.via.common.crawler.impl.client.CareerNetMajorClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CareerNetMajorCrawlerImpl implements MajorCrawler {
    private final CareerNetMajorClient careerNetMajorClient;

    @Value("${app.api.career-net.api-key}")
    private String apiKey;

    private static final String SVC_TYPE = "api";
    private static final String SVC_CODE = "MAJOR";
    private static final String CONTENT_TYPE = "json";
    private static final String GUBUN = "univ_list";

    @Override
    public List<CrawledMajor> findAll(int page, int pageSize) {
        return careerNetMajorClient.getMajorResponse(
                apiKey,
                SVC_TYPE,
                SVC_CODE,
                CONTENT_TYPE,
                GUBUN,
                null,
                page,
                pageSize,
                null
        ).getDataSearch().getContent()
                .stream()
                .map(majorInfo -> new CrawledMajor(majorInfo.getSeq(), majorInfo.getMClass()))
                .toList();
    }

    @Override
    public int getTotalCount() {
        return careerNetMajorClient.getMajorResponse(
                apiKey,
                SVC_TYPE,
                SVC_CODE,
                CONTENT_TYPE,
                GUBUN,
                null,
                1,
                1,
                null
        ).getDataSearch().getContent().getFirst().getTotalCount();
    }
}

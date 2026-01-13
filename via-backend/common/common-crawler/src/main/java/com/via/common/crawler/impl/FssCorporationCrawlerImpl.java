package com.via.common.crawler.impl;

import com.via.common.crawler.CorporationCrawler;
import com.via.common.crawler.dto.CrawledCorporation;
import com.via.common.crawler.impl.client.FssCorporationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FssCorporationCrawlerImpl implements CorporationCrawler {
    @Value("${app.api.fss-corporation-basic-info.api-key}")
    private String apiKey;

    private static final String RESULT_TYPE = "json";

    private final FssCorporationClient fssCorporationClient;

    @Override
    public List<CrawledCorporation> findAll(int page, int pageSize) {
        return fssCorporationClient.getCompanyList(
                apiKey,
                page,
                pageSize,
                RESULT_TYPE,
                null,
                null
        ).getResponse().getBody().getItems().getItem()
                .stream()
                .map(corporationInfo -> new CrawledCorporation(corporationInfo.getCrno(), corporationInfo.getCorpNm()))
                .toList();
    }

    @Override
    public int getTotalCount() {
        return fssCorporationClient.getCompanyList(
                apiKey,
                1,
                1,
                RESULT_TYPE,
                null,
                null
        ).getResponse().getBody().getTotalCount();
    }
}

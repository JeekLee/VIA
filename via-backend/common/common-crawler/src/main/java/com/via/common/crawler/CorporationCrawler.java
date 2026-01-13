package com.via.common.crawler;

import com.via.common.crawler.dto.CrawledCorporation;

import java.util.List;

public interface CorporationCrawler {
    List<CrawledCorporation> findAll(int page, int pageSize);
    int getTotalCount();
}

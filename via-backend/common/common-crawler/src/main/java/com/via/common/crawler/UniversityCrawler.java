package com.via.common.crawler;

import com.via.common.crawler.dto.CrawledUniversity;

import java.util.List;

public interface UniversityCrawler {
    List<CrawledUniversity> findAll(int page, int pageSize);
    int getTotalCount();
}

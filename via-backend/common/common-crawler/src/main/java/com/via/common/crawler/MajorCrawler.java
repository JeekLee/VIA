package com.via.common.crawler;

import com.via.common.crawler.dto.CrawledMajor;

import java.util.List;

public interface MajorCrawler {
    List<CrawledMajor> findAll(int page, int pageSize);
    int getTotalCount();
}

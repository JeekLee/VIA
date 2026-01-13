package com.via.content.crawler;

import com.via.content.crawler.dto.CrawledCourse;
import com.via.content.domain.course.enums.CoursePlatform;

import java.util.List;

public interface CourseCrawler {
    CoursePlatform getPlatform();
    List<CrawledCourse> crawl();
}

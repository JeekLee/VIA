package com.via.runnable.batch.content.reader;

import com.via.content.crawler.CourseCrawler;
import com.via.content.crawler.dto.CrawledCourse;
import com.via.content.domain.course.enums.CoursePlatform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Component
@StepScope
@Slf4j
@RequiredArgsConstructor
public class CourseCrawlerReader implements ItemReader<CrawledCourse> {
    private final List<CourseCrawler> courseCrawlers;

    @Value("#{stepExecutionContext['platform']}")
    private String platform;

    private Iterator<CrawledCourse> courseIterator;

    @Override
    public CrawledCourse read() {
        if (courseIterator == null) {
            CoursePlatform coursePlatform = CoursePlatform.valueOf(platform);
            log.info("Crawling course of {}", coursePlatform.name());

            courseIterator = courseCrawlers.stream()
                    .filter(crawler -> crawler.getPlatform().equals(coursePlatform))
                    .findFirst()
                    .map(CourseCrawler::crawl)
                    .orElse(Collections.emptyList())
                    .iterator();
        }

        return courseIterator.hasNext() ? courseIterator.next() : null;
    }
}

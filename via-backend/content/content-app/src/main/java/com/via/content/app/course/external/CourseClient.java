package com.via.content.app.course.external;

import com.via.content.app.course.dto.CrawledCourse;
import com.via.content.domain.course.enums.CoursePlatform;

import java.util.List;

public interface CourseClient {
    List<CrawledCourse> crawl(CoursePlatform platform);
}

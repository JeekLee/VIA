package com.via.runnable.batch.content.processor;

import com.via.content.app.course.dto.CreateCourse;
import com.via.content.crawler.dto.CrawledCourse;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCourseProcessor implements ItemProcessor<CrawledCourse, CreateCourse> {
    @Override
    public CreateCourse process(CrawledCourse crawledCourse) {
        return new CreateCourse(
                crawledCourse.url(),
                crawledCourse.platform(),
                crawledCourse.title(),
                crawledCourse.instructor(),
                crawledCourse.description(),
                crawledCourse.difficulty(),
                crawledCourse.rating(),
                crawledCourse.skillNames()
        );
    }
}

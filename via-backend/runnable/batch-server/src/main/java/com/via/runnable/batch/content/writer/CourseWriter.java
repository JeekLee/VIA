package com.via.runnable.batch.content.writer;

import com.via.content.app.course.dto.CreateCourse;
import com.via.content.app.course.service.CourseUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@StepScope
@RequiredArgsConstructor
@Slf4j
public class CourseWriter implements ItemWriter<CreateCourse> {
    private final CourseUseCase courseUseCase;

    @Override
    public void write(Chunk<? extends CreateCourse> chunk) {
        List<CreateCourse> createCourses = chunk.getItems()
                .stream()
                .map(u -> (CreateCourse) u)
                .toList();

        courseUseCase.createAndSaveCourses(createCourses);
    }
}

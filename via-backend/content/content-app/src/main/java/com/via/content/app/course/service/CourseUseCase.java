package com.via.content.app.course.service;

import com.via.content.app.course.dto.CreateCourse;
import com.via.content.app.course.repository.CourseRepository;
import com.via.content.app.skill.repository.SkillRepository;
import com.via.content.domain.course.model.Course;
import com.via.content.domain.course.model.Skill;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseUseCase {
    private final SkillRepository skillRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public void createAndSaveCourses(List<CreateCourse> createCourses) {
        List<Course> courses = createCourses.stream()
                .map(this::create)
                .toList();
        courseRepository.saveAll(courses);
    }

    private Course create(CreateCourse createCourse) {
        Course course = Course.create(
                createCourse.url(),
                createCourse.platform(),
                createCourse.title(),
                createCourse.instructor(),
                createCourse.description(),
                createCourse.difficulty(),
                createCourse.rating()
        );
        List<Skill> skills = createCourse.skillNames().stream()
                .map(skillRepository::searchSimilar)
                .flatMap(Optional::stream)
                .toList();
        return course.updateSkills(skills);
    }

    @Transactional(readOnly = true)
    public List<String> searchSimilarAndConvertAsString(String input) {
        List<Course> courses = courseRepository.searchSimilarByQuery(input);
        return courses.stream().map(Course::toString).toList();
    }
}

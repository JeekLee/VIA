package com.via.content.infra.course.database.repository.custom.impl;

import com.via.content.domain.course.model.Course;
import com.via.content.domain.course.model.id.CourseId;
import com.via.content.infra.course.database.repository.custom.CourseJpaRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CourseJpaRepositoryCustomImpl implements CourseJpaRepositoryCustom {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Course> saveAll(List<Course> courses) {
        if (courses.isEmpty()) return List.of();

        bulkInsertCourses(courses);
        creatTemporaryTableAndInsertUrls(courses);
        deleteCourseSkillsByTemporaryTable();
        Map<String, Long> urlToIdMap = getUrlToIdMapFromTemporaryTable();
        Map<Course, Long> courseIdMap = courses.stream()
                .collect(Collectors.toMap(
                        course -> course,
                        course -> urlToIdMap.get(course.url().toString())
                ));
        bulkInsertCourseSkills(courses, courseIdMap);
        dropTemporaryTable();
        return courses.stream()
                .map(course -> course.withId(new CourseId(courseIdMap.get(course))))
                .toList();
    }

    private void bulkInsertCourses(List<Course> courses) {
        String sql = """
            INSERT INTO content.course
            (url, platform, title, instructor, description, difficulty, rating, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                platform = VALUES(platform),
                title = VALUES(title),
                instructor = VALUES(instructor),
                description = VALUES(description),
                difficulty = VALUES(difficulty),
                rating = VALUES(rating),
                updated_at = VALUES(updated_at)
        """;

        jdbcTemplate.batchUpdate(sql, courses, 1000, (ps, course) -> {
            ps.setString(1, course.url().toString());
            ps.setString(2, course.platform().name());
            ps.setString(3, course.title());
            ps.setString(4, course.instructor());
            ps.setString(5, course.description());
            ps.setString(6, course.difficulty().name());
            ps.setFloat(7, course.rating());
            ps.setTimestamp(8, Timestamp.valueOf(course.createdAt()));
            ps.setTimestamp(9, Timestamp.valueOf(course.updatedAt()));
        });
    }

    private void creatTemporaryTableAndInsertUrls(List<Course> courses) {
        jdbcTemplate.execute("CREATE TEMPORARY TABLE temp_course_urls (url VARCHAR(500) COLLATE utf8mb4_unicode_ci PRIMARY KEY)");
        String sql = "INSERT INTO temp_course_urls (url) VALUES (?)";
        jdbcTemplate.batchUpdate(sql, courses, 1000, (ps, course) -> ps.setString(1, course.url().toString()));
    }

    private void deleteCourseSkillsByTemporaryTable() {
        String sql = """
            DELETE cs FROM content.course_skill cs
            INNER JOIN content.course c ON cs.course_id = c.id
            INNER JOIN temp_course_urls t ON c.url = t.url
        """;
        jdbcTemplate.update(sql);
    }

    private Map<String, Long> getUrlToIdMapFromTemporaryTable() {
        String sql = """
            SELECT c.url, c.id
            FROM content.course c
            INNER JOIN temp_course_urls t ON c.url = t.url
        """;

        return jdbcTemplate.query(sql, rs -> {
            Map<String, Long> map = new HashMap<>();
            while (rs.next()) {
                map.put(rs.getString("url"), rs.getLong("id"));
            }
            return map;
        });
    }

    private void dropTemporaryTable() {
        jdbcTemplate.execute("DROP TEMPORARY TABLE IF EXISTS temp_course_urls");
    }

    private record CourseSkillId(Long courseId, Long skillId) { }

    private void bulkInsertCourseSkills(List<Course> courses, Map<Course, Long> courseIdMap) {
        List<CourseSkillId> courseSkillIds = new ArrayList<>();
        for (Course course : courses) {
            Long courseId = courseIdMap.get(course);
            for (var skill : course.skills()) {
                courseSkillIds.add(new CourseSkillId(courseId, skill.id().id()));
            }
        }

        if (courseSkillIds.isEmpty()) return;

        String sql = "INSERT IGNORE INTO content.course_skill (course_id, skill_id) VALUES (?, ?)";

        jdbcTemplate.batchUpdate(sql, courseSkillIds, 1000, (ps, courseSkillId) -> {
            ps.setLong(1, courseSkillId.courseId());
            ps.setLong(2, courseSkillId.skillId());
        });
    }
}

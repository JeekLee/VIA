package com.via.content.domain.course.model;

import com.via.content.domain.course.enums.CourseDifficulty;
import com.via.content.domain.course.enums.CoursePlatform;
import com.via.content.domain.course.model.id.CourseId;
import lombok.Builder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record Course (
        CourseId id,
        URI url,
        CoursePlatform platform,

        String title,
        String instructor,
        String description,

        CourseDifficulty difficulty,
        Float rating,
        List<Skill> skills,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static Course create(URI url, CoursePlatform platform,
                                String title, String instructor, String description,
                                CourseDifficulty difficulty, Float rating) {
        return Course.builder()
                .url(url)
                .platform(platform)
                .title(title)
                .instructor(instructor)
                .description(description)
                .difficulty(difficulty)
                .rating(rating)
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .updatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }

    public Course updateSkills(List<Skill> skills) {
        return Course.builder()
                .id(this.id)
                .url(this.url)
                .platform(this.platform)
                .title(this.title)
                .instructor(this.instructor)
                .description(this.description)
                .difficulty(this.difficulty)
                .rating(this.rating)
                .skills(skills)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }

    public Course withId(CourseId id) {
        return Course.builder()
                .id(id)
                .url(this.url)
                .platform(this.platform)
                .title(this.title)
                .instructor(this.instructor)
                .description(this.description)
                .difficulty(this.difficulty)
                .rating(this.rating)
                .skills(this.skills)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    @Override
    public String toString() {
        String skillsAsString = this.skills != null
                ? this.skills().stream().map(Skill::name).collect(Collectors.joining(", "))
                : "";
        return "ID: " + this.id().id() + "\n" +
                "Title: " + this.title() + "\n" +
                "Instructor: " + this.instructor() + "\n" +
                "Description: " + this.description() + "\n" +
                "Level: " + this.difficulty().name() + "\n" +
                "Rating: " + this.rating() + "\n" +
                "Skills: " + skillsAsString;
    }
}

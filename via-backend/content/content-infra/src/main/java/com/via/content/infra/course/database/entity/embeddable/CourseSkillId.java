package com.via.content.infra.course.database.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseSkillId implements Serializable {

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "skill_id")
    private Long skillId;

    private CourseSkillId(Long courseId, Long skillId) {
        this.courseId = courseId;
        this.skillId = skillId;
    }

    public static CourseSkillId of(Long courseId, Long skillId) {
        return new CourseSkillId(courseId, skillId);
    }
}
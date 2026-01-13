package com.via.content.infra.course.database.entity;

import com.via.content.infra.course.database.entity.embeddable.CourseSkillId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "course_skill", catalog = "content")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseSkillJpaEntity {
    
    @EmbeddedId
    private CourseSkillId id;

    private CourseSkillJpaEntity(CourseSkillId id) {
        this.id = id;
    }

    public static CourseSkillJpaEntity of(Long courseId, Long skillId) {
        return new CourseSkillJpaEntity(CourseSkillId.of(courseId, skillId));
    }
    
    public Long getCourseId() {
        return id.getCourseId();
    }
    
    public Long getSkillId() {
        return id.getSkillId();
    }
}
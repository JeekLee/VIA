package com.via.content.crawler.impl;

import com.via.content.domain.course.enums.CourseDifficulty;
import org.jsoup.nodes.Element;

import java.net.URI;
import java.util.List;

public interface CourseExtractorBundle {

    URI extractURI(Element element);

    String extractImageUrl(Element element);

    String extractTitle(Element element);

    String extractInstructor(Element element);

    String extractDescription(Element element);

    CourseDifficulty extractCourseDifficulty(Element element);

    Float extractRating(Element element);

    List<String> extractSkillNames(Element element);
}
